
import definitions::*;			      // includes package "definitions"
module ALU(
  input        [ 4:0] OP,		   	  // ALU opcode, part of microcode
  input               CI,             // shift or carry in from right (LSB side)
  input        [ 7:0] INPUTA,	   	  // data inputs
                      INPUTB,
  output logic [ 7:0] OUT,		      // or:  output reg [ 7:0] OUT,
  output logic        CO,             // shift or carry out to left (MSB side)
                      ISBRANCH				  // 1: OUT = 0
  );
	 
//  op_mne op_mnemonic;			  // type enum: used for convenient waveform viewing
  assign ISBRANCH = (OP == 17)?(INPUTA == 0):(INPUTA !=0);	
	
  always_comb begin
	case(OP)
	  kADD	: {CO,OUT} = INPUTA+INPUTB+CI;  //0000  		
	  kSUB	: {CO,OUT} = {CI,INPUTA-INPUTB};		//0001		
	  kSRSE	: {OUT,CO} = 9'(signed'(INPUTA));	//0002			
	  kSRLP	: {OUT,CO} = INPUTA;  //0003
	  kSRLG	: {CO,OUT} = {CI,INPUTA[7:1]}; //0004
	  kSLLP	: {CO,OUT} = {INPUTA,1'b0}; //5
	  kSLLG	: {CO,OUT} = {INPUTA[6:0],CI};//6
	  kNOT	: {CO,OUT} = ~INPUTA;//7
	  kSRI	: {CO,OUT} = {CI,INPUTA>>INPUTB};//8
	  kNOP	: {CO,OUT} = {CI,INPUTA};//9
	  kGETL	: {CO,OUT} = {CI,8'(INPUTA[3:0])};//10
	  kGETU	: {CO,OUT} = {CI,8'(INPUTA[7:4])};//11
	  kINC	: {CO,OUT} = INPUTA+1;  //12  		
	  kDEC	: {CO,OUT} = {CI,INPUTA-1};		//13
	  kSLT   : {CO,OUT} = {CI,8'(INPUTA < INPUTB? 1: 0)};//14
	  kSETL	: {CO,OUT} = {CI,INPUTA[7:4],INPUTB[3:0]};//15
	  kSETU	: {CO,OUT} = {CI,INPUTB[3:0],INPUTA[3:0]};//16
	  kBRH	: {CO,OUT} =  9'b0; 
	  kCLR   : {CO,OUT} =  9'b0;
	  kNOPB  : {CO,OUT} = {CI,INPUTB};//19
	  default : {CO,OUT} = 9'b0;
	endcase
//	case(OUT)
//	  8'b0    : ISBRANCH = 1'b1;
//	  default : ISBRANCH = 1'b0;
//	endcase
//$display("ALU Out %d \n",OUT);
    //op_mnemonic = op_mne'(OP);

  end

endmodule
