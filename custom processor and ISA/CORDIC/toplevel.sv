// Create Date:    2017.01.25
// Design Name: 
// Module Name:    TopLevel 
// partial only
module toplevel(
    input     start,
	 input     CLK,
    output    halt
    );

wire[ 8:0] PC, JP;           // program count
wire[ 8:0] inst;  // our 9-bit opcode
wire[ 7:0] Read,Readr0, INPUTA, INPUTB; // reg_file outputs
wire       Readov,Writeov;
wire[ 7:0] writeValue,
			  dataOut,
			  OUT;
wire[ 3:0] writeSrc;
wire[ 4:0] ALUOP;
wire[ 1:0] sel;
wire       RegDst,
			  RegWrite,
			  MemRead,
		     MemWrite,
           Branch,
			  MemtoReg,
			  ALUSrc1,
			  ALUSrc2, 
			  ISBRANCH;
logic      cycle_ct;


assign writeSrc = RegDst? inst[3:0]:0; //1 for rd 0 for r0  
assign writeValue = MemtoReg? dataOut:OUT;  
assign INPUTA = ALUSrc1? Read:Readr0;
assign INPUTB = ALUSrc2? Read:inst[3:0];


control control1 (
 .OP (inst[8:4]), 
 .RegDst,
 .RegWrite,
 .MemRead,
 .MemWrite,
 .Branch,
 .MemtoReg,
 .ALUSrc1,
 .ALUSrc2,
 .sel, 
 .HALT(halt),
 .ALUOP
);

program_counter	program_counter1(
	.Branch(Branch & ISBRANCH),
	.Init(start),
	.Halt(halt),
	.CLK(CLK),
	.JP(JP),
	.PC(PC)
	);
	
	
LUT LUT1
(
	.index (inst[3:0]),
	.JP(JP));
	

InstROM instr_rom(.InstAddress(PC),    // instantiate instruction ROM
              .InstOut(inst));    

reg_file reg_file1
(
    .CLK(CLK),
	 .RegWrite(RegWrite),
    .src(inst[3:0]),
	 .writeSrc(writeSrc),
    .writeValue(writeValue),
	 .Writeov(Writeov),
    .Read(Read),
	 .Readr0(Readr0),
	 .Readov(Readov)
    );
 
 ALU	ALU1(
	.CI(Readov),
	.INPUTA(INPUTA),
	.INPUTB(INPUTB),
	.OP(ALUOP),
	.CO(Writeov),
	.ISBRANCH(ISBRANCH),
	.OUT(OUT)
);
 
	 
datamem data_mem(.dataAddress(OUT),
		  .data(Read),
		  .memWrite(MemWrite),
		  .memRead(MemRead),
		  .dataOut(dataOut),
		  .clk(CLK),
		  .sel(sel)
);  
	

	
// count number of instructions executed
always@(posedge CLK)
  if (start == 1)
  	cycle_ct <= 0;
  else if(halt == 0)
  	cycle_ct <= cycle_ct+1;

endmodule
