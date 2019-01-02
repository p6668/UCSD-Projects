module control (
	input[4:0] OP,
	output logic   RegDst,
						RegWrite,
						MemRead,
						MemWrite,
						Branch,
						MemtoReg,
						ALUSrc1,
						ALUSrc2,
						HALT,	
	output logic[4:0] ALUOP,
	output logic[1:0] sel);
	always_comb begin 
		case(OP)
		5'b00000: begin
			RegDst = 1;
			RegWrite = 1;
			MemRead = 1;
			MemWrite = 0;
			Branch = 0;
			MemtoReg = 1;
			ALUSrc1 = 0;
			ALUSrc2 = 0;
			HALT = 0;	
			ALUOP = 9;
			sel = 0;
		end
		
		5'b00001: begin
			RegDst = 0;
			RegWrite = 0;
			MemRead = 0;
			MemWrite = 1;
			Branch = 0;
			MemtoReg = 0;
			ALUSrc1 = 0;
			ALUSrc2 = 1;
			HALT = 0;
			ALUOP = 9;
			sel = 0;
		end
		
		5'b00010: begin
			RegDst = 0;
			RegWrite = 1;
			MemRead = 0;
			MemWrite = 0;
			Branch = 0;
			MemtoReg = 0;
			ALUSrc1 = 0;
			ALUSrc2 = 1;
			HALT = 0;
			ALUOP = 0;
			sel = 0;
		end
		
		5'b00011: begin
			RegDst = 0;
			RegWrite = 1;
			MemRead = 0;
			MemWrite = 0;
			Branch = 0;
			MemtoReg = 0;
			ALUSrc1 = 0;
			ALUSrc2 = 1;
			HALT = 0;
			ALUOP = 1;
			sel = 0;
		end
		
		5'b00100: begin //inc
			RegDst = 1;
			RegWrite = 1;
			MemRead = 0;
			MemWrite = 0;
			Branch = 0;
			MemtoReg = 0;
			ALUSrc1 = 1;
			ALUSrc2 = 0;
			HALT = 0;
			ALUOP = 12;
			sel = 0;
		end
		
		5'b00101: begin //dec
			RegDst = 1;
			RegWrite = 1;
			MemRead = 0;
			MemWrite = 0;
			Branch = 0;
			MemtoReg = 0;
			ALUSrc1 = 1;
			ALUSrc2 = 0;
			HALT = 0;
			ALUOP = 13;
			sel = 0;
		end
		
		5'b00110: begin
			RegDst = 1;
			RegWrite = 1;
			MemRead = 0;
			MemWrite = 0;
			Branch = 0;
			MemtoReg = 0;
			ALUSrc1 = 1;
			ALUSrc2 = 0;
			HALT = 0;
			ALUOP = 2;
			sel = 0;
		end
		
		5'b00111: begin
			RegDst = 1;
			RegWrite = 1;
			MemRead = 0;
			MemWrite = 0;
			Branch = 0;
			MemtoReg = 0;
			ALUSrc1 = 1;
			ALUSrc2 = 0;
			HALT = 0;
			ALUOP = 3;
			sel = 0;
		end
		
		5'b01000: begin
			RegDst = 1;
			RegWrite = 1;
			MemRead = 0;
			MemWrite = 0;
			Branch = 0;
			MemtoReg = 0;
			ALUSrc1 = 1;
			ALUSrc2 = 0;
			HALT = 0;
			ALUOP = 4;
			sel = 0;
		end
		
		5'b01001: begin
			RegDst = 1;
			RegWrite = 1;
			MemRead = 0;
			MemWrite = 0;
			Branch = 0;
			MemtoReg = 0;
			ALUSrc1 = 1;
			ALUSrc2 = 0;
			HALT = 0;
			ALUOP = 5;
			sel = 0;
		end
		
		
		5'b01010: begin
			RegDst = 1;
			RegWrite = 1;
			MemRead = 0;
			MemWrite = 0;
			Branch = 0;
			MemtoReg = 0;
			ALUSrc1 = 1;
			ALUSrc2 = 0;
			HALT = 0;
			ALUOP = 6;
			sel = 0;
		end
		
		5'b01011: begin
			RegDst = 1;
			RegWrite = 1;
			MemRead = 0;
			MemWrite = 0;
			Branch = 0;
			MemtoReg = 0;
			ALUSrc1 = 0;
			ALUSrc2 = 0;
			HALT = 0;
			ALUOP = 9;
			sel = 0;
		end
		
		5'b01100: begin
			RegDst = 1;
			RegWrite = 1;
			MemRead = 0;
			MemWrite = 0;
			Branch = 0;
			MemtoReg = 0;
			ALUSrc1 = 1;
			ALUSrc2 = 0;
			HALT = 0;
			ALUOP = 7;
			sel = 0;
		end
		
			
		5'b01101: begin //slt
			RegDst = 0;
			RegWrite = 1;
			MemRead = 0;
			MemWrite = 0;
			Branch = 0;
			MemtoReg = 0;
			ALUSrc1 = 0;
			ALUSrc2 = 1;
			HALT = 0;
			ALUOP = 14;
			sel = 0;
		end
		
		5'b01110: begin //lwl
			RegDst = 1;
			RegWrite = 1;
			MemRead = 1;
			MemWrite = 0;
			Branch = 0;
			MemtoReg = 1;
			ALUSrc1 = 0;
			ALUSrc2 = 0;
			HALT = 0;
			ALUOP = 9;
			sel = 1;
		end
		
		
		5'b01111: begin //lwu
			RegDst = 1;
			RegWrite = 1;
			MemRead = 1;
			MemWrite = 0;
			Branch = 0;
			MemtoReg = 1;
			ALUSrc1 = 0;
			ALUSrc2 = 0;
			HALT = 0;
			ALUOP = 9;
			sel = 2;
		end
		
		5'b10000: begin
			RegDst = 0;
			RegWrite = 1;
			MemRead = 0;
			MemWrite = 0;
			Branch = 0;
			MemtoReg = 0;
			ALUSrc1 = 1;
			ALUSrc2 = 0;
			HALT = 0;
			ALUOP = 10;
			sel = 0;
		end
		
		5'b10001: begin
			RegDst = 0;
			RegWrite = 1;
			MemRead = 0;
			MemWrite = 0;
			Branch = 0;
			MemtoReg = 0;
			ALUSrc1 = 1;
			ALUSrc2 = 0;
			HALT = 0;
			ALUOP = 11;
			sel = 0;
		end
		
		5'b10010: begin
			RegDst = 0;
			RegWrite = 1;
			MemRead = 0;
			MemWrite = 0;
			Branch = 0;
			MemtoReg = 0;
			ALUSrc1 = 0;
			ALUSrc2 = 0;
			HALT = 0;
			ALUOP = 16;
			sel = 0;
		end
		
		5'b10011: begin
			RegDst = 0;
			RegWrite = 1;
			MemRead = 0;
			MemWrite = 0;
			Branch = 0;
			MemtoReg = 0;
			ALUSrc1 = 0;
			ALUSrc2 = 0;
			HALT = 0;
			ALUOP = 15;
			sel = 0;
		end
		
		
		5'b10100: begin
			RegDst = 0;
			RegWrite = 1;
			MemRead = 0;
			MemWrite = 0;
			Branch = 0;
			MemtoReg = 0;
			ALUSrc1 = 0;
			ALUSrc2 = 0;
			HALT = 0;
			ALUOP = 0;
			sel = 0;
		end
		
		5'b10101: begin
			RegDst = 0;
			RegWrite = 1;
			MemRead = 0;
			MemWrite = 0;
			Branch = 0;
			MemtoReg = 0;
			ALUSrc1 = 0;
			ALUSrc2 = 0;
			HALT = 0;
			ALUOP = 8;
			sel = 0;
		end
		
		5'b10110: begin //bez
			RegDst = 0;
			RegWrite = 0;
			MemRead = 0;
			MemWrite = 0;
			Branch = 1;
			MemtoReg = 0;
			ALUSrc1 = 0;
			ALUSrc2 = 0;
			HALT = 0;
			ALUOP = 17;
			sel = 0;
		end
		
		5'b10111: begin //bnez
			RegDst = 0;
			RegWrite = 0;
			MemRead = 0;
			MemWrite = 0;
			Branch = 1;
			MemtoReg = 0;
			ALUSrc1 = 0;
			ALUSrc2 = 0;
			HALT = 0;
			ALUOP = 18;
			sel = 0;
		end
		
		5'b11000: begin
			RegDst = 0;
			RegWrite = 0;
			MemRead = 0;
			MemWrite = 0;
			Branch = 0;
			MemtoReg = 0;
			ALUSrc1 = 0;
			ALUSrc2 = 0;
			HALT = 0;
			ALUOP = 18;
			sel = 0;
		end
		
		
		5'b11001: begin
			RegDst = 0;
			RegWrite = 0;
			MemRead = 0;
			MemWrite = 0;
			Branch = 0;
			MemtoReg = 0;
			ALUSrc1 = 0;
			ALUSrc2 = 0;
			HALT = 1;
			ALUOP = 9;
			sel = 0;
		end
		
		default: begin
			RegDst = 0;
			RegWrite = 0;
			MemRead = 0;
			MemWrite = 0;
			Branch = 0;
			MemtoReg = 0;
			ALUSrc1 = 0;
			ALUSrc2 = 0;
			HALT = 0;
			ALUOP = 9;
			sel = 0;
		end
		
		endcase
	end
endmodule
