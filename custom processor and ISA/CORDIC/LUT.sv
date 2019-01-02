
module LUT(
	input [3:0] index,
	output logic [8:0] JP);
	
	always_comb begin 
	case(index)
		4'b0000: JP = 27;
		4'b0001: JP = 34;
		4'b0010: JP = 39;
		4'b0011: JP = 53;
		4'b0100: JP = 58;
		4'b0101: JP = 61;
		4'b0110: JP = 66;
		4'b0111: JP = 112;
		4'b1000: JP = 123;
		4'b1001: JP = 128;
		4'b1010: JP = 131;
		4'b1011: JP = 136;
		4'b1100: JP = 171;
		default: JP = 0;
	endcase

	end

endmodule

