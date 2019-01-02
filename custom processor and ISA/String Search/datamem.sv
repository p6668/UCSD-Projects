module datamem(
  input       [ 7:0] dataAddress,
  input       [ 7:0] data,
  input       [ 1:0] sel,
  input 		  memWrite, memRead, clk,	
  output logic[ 7:0] dataOut);
  
	 
  logic [7:0] core[2**(8)];
  
  
	
	
  
  always @(posedge clk) begin
	if(memWrite) 
		core[dataAddress] = data;
	
  end
  always @(negedge clk) begin
	  if(memRead) begin
			if(sel == 0)
				dataOut <= core[dataAddress];
			else if(sel == 1)
				dataOut <= 8'(core[dataAddress][3:0]);
			else if(sel == 2)
				dataOut <= 8'(core[dataAddress][7:4]);
			else 
				dataOut <= 8'bZ;
		end
	end
		

endmodule
