
module LUT(
	input [3:0] index,
	output logic [8:0] JP);
    
	always_comb begin 
	case(index)
		4'b0000: JP = 16;
		4'b0001: JP = 36;
		4'b0010: JP = 41;
		4'b0011: JP = 52;
		4'b0100: JP = 62;
		4'b0101: JP = 69;
		default: JP = 0;
	endcase

	end

endmodule

