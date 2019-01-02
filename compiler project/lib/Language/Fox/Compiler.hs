{-# LANGUAGE TypeSynonymInstances #-}
{-# LANGUAGE FlexibleInstances    #-}

--------------------------------------------------------------------------------
-- | The entry point for the compiler: a function that takes a Text
--   representation of the source and returns a (Text) representation
--   of the assembly-program string representing the compiled version
--------------------------------------------------------------------------------

module Language.Fox.Compiler ( compiler, compile ) where

import           Prelude                  hiding (compare)
import           Control.Arrow                    ((>>>))
import           Data.Maybe
import           Data.Bits                       (shift)
import           Language.Fox.Types      hiding (Tag)
import           Language.Fox.Parser     (parse)
import           Language.Fox.Checker    (check, errUnboundVar)
import           Language.Fox.Normalizer (anormal)
import           Language.Fox.Asm        (asm)


--------------------------------------------------------------------------------
compiler :: FilePath -> Text -> Text
--------------------------------------------------------------------------------
-- compiler f = parse f >=> check >-> anormal >-> tag >-> compile >-> asm

compiler f = parse f >>> check >>> anormal >>> tag >>> compile >>> asm

--------------------------------------------------------------------------------
-- | The compilation (code generation) works with AST nodes labeled by @Tag@
--------------------------------------------------------------------------------
type Tag   = (SourceSpan, Int)
type AExp  = AnfExpr Tag
type IExp  = ImmExpr Tag
type ABind = Bind    Tag
type ADcl  = Decl    Tag
type APgm  = Program Tag

instance Located Tag where
  sourceSpan = fst

instance Located a => Located (Expr a) where
  sourceSpan = sourceSpan . getLabel

--------------------------------------------------------------------------------
-- | @tag@ annotates each AST node with a distinct Int value
--------------------------------------------------------------------------------
tag :: AnfProgram SourceSpan -> APgm
--------------------------------------------------------------------------------
tag = label

--------------------------------------------------------------------------------
compile :: APgm -> [Instruction]
--------------------------------------------------------------------------------
compile (Prog ds e) = compileBody emptyEnv e
                   ++ concatMap compileDecl ds

compileDecl :: ADcl -> [Instruction]
compileDecl (Decl f xs e t) = ILabel (DefStart (bindId f) 0)
                            : compileBody env e
  where
    env                     = fromListEnv (zip (bindId <$> xs) [-2, -3..])

compileBody :: Env -> AExp -> [Instruction]
compileBody env e = funInstrs (countVars e) (compileEnv env e)

-- | @funInstrs n body@ returns the instructions of `body` wrapped
--   with code that sets up the stack (by allocating space for n local vars)
--   and restores the callees stack prior to return.

funInstrs :: Int -> [Instruction] -> [Instruction]
funInstrs n instrs = funEntry n ++ instrs ++ funExit

-- instructions for setting up stack for `n` local vars
funEntry :: Int -> [Instruction]
funEntry n = [ IPush (Reg EBP)                       -- save caller's ebp
             , IMov  (Reg EBP) (Reg ESP)             -- set callee's ebp
             , ISub  (Reg ESP) (Const (4 * n))       -- allocate n local-vars
             ]
          ++ [ clearStackVar i | i <- [1..n] ]       -- zero out stack-vars

-- clean up stack & labels for jumping to error
funExit :: [Instruction]
funExit   = [ IMov (Reg ESP) (Reg EBP)          -- restore callee's esp
            , IPop (Reg EBP)                    -- restore callee's ebp
            , IRet                              -- jump back to caller
            ]

clearStackVar :: Int -> Instruction
clearStackVar i = IMov (Sized DWordPtr (stackVar i)) (Const 0)

--------------------------------------------------------------------------------
-- | @countVars e@ returns the maximum stack-size needed to evaluate e,
--   which is the maximum number of let-binds in scope at any point in e.
--------------------------------------------------------------------------------
countVars :: AnfExpr a -> Int
--------------------------------------------------------------------------------
countVars (Let _ e b _)  = max (countVars e)  (1 + countVars b)
countVars (If v e1 e2 _) = maximum [countVars v, countVars e1, countVars e2]
countVars _              = 0


tupleAddr :: Int -> Arg
tupleAddr n = Sized DWordPtr (RegOffset (4 * n) EAX)

tupleCopy :: [Arg] -> [Instruction]
tupleCopy args 
  = [IMov (Reg EBX) (Const (length args)), IShl (Reg EBX) (Const 1), IMov (tupleAddr 0) (Reg EBX)]
   ++ [IMov (Reg EBX) (Const 0), IMov (tupleAddr 1) (Reg EBX)]
   ++ (copyHelper args 2)

copyHelper :: [Arg] -> Int -> [Instruction]
copyHelper [] i = []
copyHelper (h:t) i = [IMov (Reg EBX) h, IMov (tupleAddr i) (Reg EBX)]
                   ++ (copyHelper t (i + 1))

tupleAlloc :: Int -> [Instruction]
tupleAlloc n 
  | n `mod` 2 == 0 = [IMov (Reg EAX) (Reg ESI), IAdd (Reg ESI) (Const (4 * n + 8))]
  | otherwise = [IMov (Reg EAX) (Reg ESI), IAdd (Reg ESI) (Const (4 * n + 12))]

listToArgs :: Env -> [IExp] -> [Arg]
listToArgs env es = map (immArg env) es

unsetTag :: Reg -> Ty -> [Instruction]
unsetTag r ty = [ISub (Reg EAX) (typeTag ty)]

setTag :: Reg -> Ty -> [Instruction]
setTag r ty = [ IAdd (Reg r) (typeTag ty) ]

getElement :: Env -> IExp -> [Instruction]
getElement env i = [ IMov (Reg EBX) (immArg env i),
                     ISar (Reg EBX) (Const 1),
                     IMul (Reg EBX) (Const 4),
                     IAdd (Reg EBX) (Const 8),
                     IAdd (Reg EAX) (Reg EBX),
                     IMov (Reg EAX) (Sized DWordPtr (RegOffset 0 EAX))]

checkIndexErrors ::  Env -> IExp -> IExp -> [Instruction]
checkIndexErrors env vE vI = [ IMov (Reg EAX) (immArg env vE)]
                              ++ unsetTag EAX TTuple ++
                               [IMov (Reg EAX) (Sized DWordPtr (RegOffset 0 EAX)),
                               IMov (Reg EBX) (immArg env vI),
                               ISar (Reg EBX) (Const 1),
                               ISar (Reg EAX) (Const 1),
                               ISub (Reg EAX) (Const 1),
                               ICmp (Reg EAX) (Reg EBX),
                               IJl  (DynamicErr IndexHigh),
                               IMov (Reg EAX) (Const 0),
                               ISar (Reg EAX) (Const 1),
                               ICmp (Reg EAX) (Reg EBX),
                               IJg  (DynamicErr IndexLow)]  
--------------------------------------------------------------------------------
compileEnv :: Env -> AExp -> [Instruction]
--------------------------------------------------------------------------------
compileEnv env v@(Number {})     = [ compileImm env v  ]

compileEnv env v@(Boolean {})    = [ compileImm env v  ]

compileEnv env v@(Id {})         = [ compileImm env v  ]

-- "clear" the stack position for 'x' after executing these instructions for e2
compileEnv env (Let x e1 e2 l)  =    compileEnv env e1 
                                  ++ [IMov (stackVar i) (Reg EAX)]
                                  ++ compileEnv env' e2
                                  ++ [clearStackVar i]
   where
    (i, env')               = pushEnv x env
-- compileEnv env e@(Let {})        = is ++ compileEnv env' body
--   where
--     (env', is)                   = compileBinds env [] binds
--     (binds, body)                = exprBinds e

compileEnv env (Prim1 o v l)     = compilePrim1 l env o v

compileEnv env (Prim2 o v1 v2 l) = compilePrim2 l env o v1 v2

compileEnv env (If v e1 e2 l)    = assertType env v TBoolean
                                ++ IMov (Reg EAX) (immArg env v)
                                 : ICmp (Reg EAX) (repr False)
                                 : branch l IJe i1s i2s
  where
    i1s                          = compileEnv env e1
    i2s                          = compileEnv env e2


compileEnv env (Tuple es l)      = tupleReserve l (tupleSize (length es)) ++ -- DO NOT MODIFY THIS LINE 
                                   tupleAlloc (length es)               
                                   ++ tupleCopy (listToArgs env es)
                                   ++ setTag   EAX     TTuple             

compileEnv env (GetItem vE vI _) =  assertType env vE TTuple
                                    ++ assertType env vI TNumber    
                                    ++ checkIndexErrors env vE vI      
                                    ++ [ IMov (Reg EAX) (immArg env vE) ]   
                                    ++ unsetTag EAX TTuple                   
                                    ++ getElement env vI

compileEnv env (App f vs _)      = call (DefStart f 0) (param env <$> vs)

compileImm :: Env -> IExp -> Instruction
compileImm env v = IMov (Reg EAX) (immArg env v)

compileBind :: Env -> (ABind, AExp) -> (Env, Int, [Instruction])
compileBind env (x, e) = (env', xi, is)
  where
    is                 = compileEnv env e
                      ++ [IMov (stackVar xi) (Reg EAX)]
    (xi, env')         = pushEnv x env

compilePrim1 :: Tag -> Env -> Prim1 -> IExp -> [Instruction]
compilePrim1 l env Add1    v = compilePrim2 l env Plus  v (Number 1 l)
compilePrim1 l env Sub1    v = compilePrim2 l env Minus v (Number 1 l)
compilePrim1 l env IsNum v =  compileImm env v:
                            [ IAnd (Reg EAX) (typeMask TNumber)
                            , IShl (Reg EAX)  (Const 31)
                            , IXor (Reg EAX) (repr True)
                            ] 
compilePrim1 l env IsBool v =  compileImm env v:
                              [ IAnd (Reg EAX) (HexConst 0x00000007),
                                ICmp (Reg EAX) (HexConst 0x00000007),
                                IMov (Reg EAX) (repr True),
                                IJe  (BranchDone (snd l)),
                                IMov (Reg EAX) (repr False),
                                ILabel (BranchDone (snd l))] 
compilePrim1 l env IsTuple v =   compileImm env v:
                              [ IAnd (Reg EAX) (HexConst 0x00000007),
                                ICmp (Reg EAX) (HexConst 0x00000001),
                                IMov (Reg EAX) (repr True),
                                IJe  (BranchDone (snd l)),
                                IMov (Reg EAX) (repr False),
                                ILabel (BranchDone (snd l))] 
compilePrim1 _ env Print   v = call (Builtin "print") [param env v]

compilePrim2 :: Tag -> Env -> Prim2 -> IExp -> IExp -> [Instruction]
compilePrim2 _ env Plus    = arith     env addOp
compilePrim2 _ env Minus   = arith     env subOp
compilePrim2 _ env Times   = arith     env mulOp
compilePrim2 l env Less    = compare l env IJl (Just TNumber)
compilePrim2 l env Greater = compare l env IJg (Just TNumber)
compilePrim2 l env Equal   = compare l env IJe Nothing

immArg :: Env -> IExp -> Arg
immArg _   (Number n _)  = repr n
immArg _   (Boolean b _) = repr b
immArg env e@(Id x _)    = stackVar (fromMaybe err (lookupEnv x env))
  where
    err                  = abort (errUnboundVar (sourceSpan e) x)
immArg _   e             = panic msg (sourceSpan e)
  where
    msg                  = "Unexpected non-immExpr in immArg: " ++ show (strip e)

strip = fmap (const ())

--------------------------------------------------------------------------------
-- | Arithmetic
--------------------------------------------------------------------------------
arith :: Env -> AOp -> IExp -> IExp  -> [Instruction]
--------------------------------------------------------------------------------
arith env aop v1 v2
  =  assertType env v1 TNumber
  ++ assertType env v2 TNumber
  ++ IMov (Reg EAX) (immArg env v1)
   : IMov (Reg EBX) (immArg env v2)
   : aop (Reg EAX) (Reg EBX)

addOp :: AOp
addOp a1 a2 = [ IAdd a1 a2
              , overflow
              ]

subOp :: AOp
subOp a1 a2 = [ ISub a1 a2
              , overflow
              ]

mulOp :: AOp
mulOp a1 a2 = [ ISar a1 (Const 1),
				IMul a1 a2
              , overflow]

overflow :: Instruction
overflow = IJo (DynamicErr ArithOverflow)

--------------------------------------------------------------------------------
-- | Dynamic Tests
--------------------------------------------------------------------------------
-- | @assertType t@ tests if EAX is a value of type t and exits with error o.w.
assertType :: Env -> IExp -> Ty -> [Instruction]
assertType env v ty
  =   cmpType env v ty
  ++ [ IJne (DynamicErr (TypeError ty))    ]

cmpType :: Env -> IExp -> Ty -> [Instruction]
cmpType env v ty
  = [ IMov (Reg EAX) (immArg env v)
    , IMov (Reg EBX) (Reg EAX)
    , IAnd (Reg EBX) (typeMask ty)
    , ICmp (Reg EBX) (typeTag  ty)
    ]

--------------------------------------------------------------------------------
-- | Comparisons
--------------------------------------------------------------------------------
-- | @compare v1 v2@ generates the instructions at the
--   end of which EAX is TRUE/FALSE depending on the comparison
--------------------------------------------------------------------------------
compare :: Tag -> Env -> COp -> Maybe Ty -> IExp -> IExp -> [Instruction]
compare l env j t v1 v2
  =  compareCheck env t v1 v2
  ++ compareVal l env j v1 v2

compareCheck :: Env -> Maybe Ty -> IExp -> IExp -> [Instruction]
compareCheck _   Nothing  _  _
  =  []
compareCheck env (Just t) v1 v2
  =  assertType env v1 t
  ++ assertType env v2 t

compareVal :: Tag -> Env -> COp -> IExp -> IExp -> [Instruction]
compareVal l env j v1 v2
   = IMov (Reg EAX) (immArg env v1)
   : IMov (Reg EBX) (immArg env v2)
   : ICmp (Reg EAX) (Reg EBX)
   : boolBranch l j

--------------------------------------------------------------------------------
-- | Assignment
--------------------------------------------------------------------------------
assign :: (Repr a) => Reg -> a -> Instruction
assign r v = IMov (Reg r) (repr v)

--------------------------------------------------------------------------------
-- | Function call
--------------------------------------------------------------------------------
call :: Label -> [Arg] -> [Instruction]
call f args
  =  [ IPush a | a <- reverse args ]
  ++ [ ICall f
     , IAdd (Reg ESP) (Const (4 * n))  ]
  where
    n = length args

param :: Env -> IExp -> Arg
param env v = Sized DWordPtr (immArg env v)

--------------------------------------------------------------------------------
-- | Branching
--------------------------------------------------------------------------------
branch :: Tag -> COp -> [Instruction] -> [Instruction] -> [Instruction]
branch l j falseIs trueIs = concat
  [ [ j lTrue ]
  , falseIs
  , [ IJmp lDone
    , ILabel lTrue  ]
  , trueIs
  , [ ILabel lDone ]
  ]
  where
    lTrue = BranchTrue i
    lDone = BranchDone i
    i     = snd l

boolBranch :: Tag -> COp -> [Instruction]
boolBranch l j = branch l j [assign EAX False] [assign EAX True]

type AOp = Arg -> Arg -> [Instruction]
type COp = Label -> Instruction

stackVar :: Int -> Arg
stackVar i = RegOffset (-4 * i) EBP

--------------------------------------------------------------------------------
-- | tuple Manipulation: use this to allocate space for a tuple
--------------------------------------------------------------------------------
-- README-GC
tupleReserve :: Tag -> Int -> [Instruction]
tupleReserve l bytes
  = [ -- check for space
      IMov (Reg EAX) (LabelVar "HEAP_END")
    , ISub (Reg EAX) (Const bytes)
    , ICmp (Reg ESI) (Reg EAX)
    , IJl  (MemCheck (snd l))   -- if ESI <= HEAP_END - size then OK else try_gc
    , IJe  (MemCheck (snd l))
    , IMov (Reg EBX) (Reg ESP)
    ]
 ++ call (Builtin "try_gc") [ Reg ESI, Const bytes, Reg EBP, Reg EBX ]
 ++ [ -- assume gc success if here; EAX holds new ESI
      IMov (Reg ESI) (Reg EAX)
    , ILabel (MemCheck (snd l))
    ]


-- | 'tupleSize n' returns the number of bytes to allocate for a tuple with 'n' elements

tupleSize :: Int -> Int
tupleSize n = 4 * roundToEven (n + 2)     -- add 2 for size, GC-WORD

roundToEven :: Int -> Int
roundToEven n
  | n `mod` 2 == 0 = n
  | otherwise      = n + 1

--------------------------------------------------------------------------------
-- | Representing Values
--------------------------------------------------------------------------------

class Repr a where
  repr :: a -> Arg

instance Repr Bool where
  repr True  = HexConst 0xffffffff
  repr False = HexConst 0x7fffffff

instance Repr Int where
  repr n = Const (fromIntegral (shift n 1))

instance Repr Integer where
  repr n = Const (fromIntegral (shift n 1))

typeTag :: Ty -> Arg
typeTag TNumber   = HexConst 0x00000000
typeTag TBoolean  = HexConst 0x7fffffff
typeTag TTuple    = HexConst 0x00000001

typeMask :: Ty -> Arg
typeMask TNumber  = HexConst 0x00000001
typeMask TBoolean = HexConst 0x7fffffff
typeMask TTuple   = HexConst 0x00000007