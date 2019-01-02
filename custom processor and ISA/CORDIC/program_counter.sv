
module program_counter(

  input Branch,
  input logic [ 8:0] JP, 		// branch/jump address
  input Init,						// initialize the pc
  input Halt,
  input CLK, 
  output logic[ 8:0] PC
  
  );
	 
  always @(posedge CLK)
	if(Init) begin
	  //Done <= 0; 
	  PC <= 0;
	end
	else if(Halt) begin
	//  Done <= 1; 
     PC <= PC;
	end
	else if(Branch) begin
	 // Done <= 0; 
     PC <= JP;
	end
	else begin
	//  Done <= 0; 
     PC <= PC+1;
	end

endmodule


