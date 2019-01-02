
module InstROM(
  input       [ 8:0] InstAddress,
  output logic[ 8:0] InstOut);
	 
 // Instructions have [4bit opcode][3bit rs or rt][2bit rt, immediate, or branch target]
	 
  logic[8:0] core[2**(9)];
  always_comb InstOut = core[InstAddress];
  
  initial begin
		$readmemb("/home/tommy/Documents/CSE141l/divide/divideMachine.txt",core);
	end

endmodule
