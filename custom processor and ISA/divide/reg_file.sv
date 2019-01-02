
module reg_file #(parameter W=8, D=4)(
    input                 CLK,
	                      RegWrite,
    input        [D-1:0] src,
	 input        [D-1:0] writeSrc,
    input        [W-1:0] writeValue,
	 input        Writeov,
    output       [W-1:0] Read,Readr0,
    output       Readov
								 
    );

// W bits wide [W-1:0] and 2**4 registers deep or just [16]	 
logic [W-1:0] core[2**D];
logic ov;

// combinational reads
assign Read = core[src];
assign Readr0 = core[0];
assign Readov = ov;

// sequential (clocked) writes
always_ff @ (posedge CLK) begin
  if (RegWrite)
    core[writeSrc] <= writeValue;
	
  ov <= Writeov;	
end
endmodule
