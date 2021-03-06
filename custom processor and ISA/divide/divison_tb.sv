module division_tb;

bit  signed [15:0] dividend;
bit  signed [ 7:0] divisor;
bit  signed [7: 0] reg1,reg2,reg3,reg4,reg5,reg6,reg0;
wire signed [15:0] quotient_beh;
logic signed [15:0] quotient_DUT;
bit          clk, start;
wire         done;
bit  [  7:0] score;            // how many correct trials

// be sure to substitute the name of your top_level module here
// also, substitute names of your ports, as needed
toplevel DUT(
  .CLK   (clk),
  .start (start),
  .halt  (done)
  );

// behavioral model to match
divison DUT1(
	dividend,divisor,quotient_beh
   );

initial begin
  #10ns  start = 1'b1;
  #10ns for (int i=0; i<256; i++)		 // clear data memory
	      DUT.data_mem.core[i] = 8'b0;

// you may preload any desired constants into your data_mem here
//    ...
// case 1
  dividend = 16'h1000;
  divisor = 8'h10;
  DUT.data_mem.core[128] = 8'h10;
  DUT.data_mem.core[129] = 8'h00;
  DUT.data_mem.core[130] = 8'h10;

// clear reg. file -- you may load any constants you wish here     
  for(int i=0; i<16; i++)
	DUT.reg_file1.core[i] = 8'b0;
	DUT.reg_file1.ov = 1'b0;
	
// load instruction ROM	-- unfilled elements will get x's -- should be harmless
  $readmemb("/home/tommy/Documents/CSE141l/divide/divideMachine.txt",DUT.instr_rom.core);
//  monitor ("dividend=%d,divisor=%d,quotient=%d", dividend, divisor, quotient);
  #10ns start = 1'b0;
  #100ns wait(done);
  #10ns  quotient_DUT = {DUT.data_mem.core[126],DUT.data_mem.core[127]};
  #10ns  $display(quotient_beh,quotient_DUT);
  if(quotient_beh == quotient_DUT)	 // score another successful trial
    score++;

// case 2
  #10ns  start = 1'b1;
  #10ns dividend = 16'h7FFF;
  divisor = 8'h7F;	
  DUT.data_mem.core[128] = dividend[15:8];
  DUT.data_mem.core[129] = dividend[ 7:0];
  DUT.data_mem.core[130] = divisor;

// clear reg. file -- you may load any constants you wish here     
  for(int i=0; i<16; i++)
	DUT.reg_file1.core[i] = 8'b0;
	DUT.reg_file1.ov = 1'b0;
// load instruction ROM	-- unfilled elements will get x's -- should be harmless
  $readmemb("/home/tommy/Documents/CSE141l/divide/divideMachine.txt",DUT.instr_rom.core);
//  monitor ("dividend=%d,divisor=%d,quotient=%d", dividend, divisor, quotient);
  #10ns start = 1'b0;
  #100ns wait(done);
  #10ns  quotient_DUT = {DUT.data_mem.core[126],DUT.data_mem.core[127]};
  #10ns  $display(quotient_beh,quotient_DUT);
  if(quotient_beh == quotient_DUT)	 // score another successful trial
    score++;

// case 3
  #10ns  start = 1'b1;
  #10ns dividend = 16'h6F;
  divisor = 8'h70;
  DUT.data_mem.core[128] = dividend[15:8];
  DUT.data_mem.core[129] = dividend[ 7:0];
  DUT.data_mem.core[130] = divisor;

// clear reg. file -- you may load any constants you wish here     
  for(int i=0; i<16; i++)
	DUT.reg_file1.core[i] = 8'b0;
	DUT.reg_file1.ov = 1'b0;
// load instruction ROM	-- unfilled elements will get x's -- should be harmless
  $readmemb("/home/tommy/Documents/CSE141l/divide/divideMachine.txt",DUT.instr_rom.core);
//  monitor ("dividend=%d,divisor=%d,quotient=%d", dividend, divisor, quotient);
  #10ns start = 1'b0;
  #100ns wait(done);
  #10ns  quotient_DUT = {DUT.data_mem.core[126],DUT.data_mem.core[127]};
  #10ns  $display(quotient_beh,quotient_DUT);
  if(quotient_beh == quotient_DUT)	 // score another successful trial
    score++;

// case 4
  #10ns  start = 1'b1;
  #10ns dividend = 16'h100;
  divisor = 8'h10;
  DUT.data_mem.core[128] = dividend[15:8];
  DUT.data_mem.core[129] = dividend[ 7:0];
  DUT.data_mem.core[130] = divisor;

// clear reg. file -- you may load any constants you wish here     
  for(int i=0; i<16; i++)
	DUT.reg_file1.core[i] = 8'b0;
	DUT.reg_file1.ov = 1'b0;
// load instruction ROM	-- unfilled elements will get x's -- should be harmless
  $readmemb("/home/tommy/Documents/CSE141l/divide/divideMachine.txt",DUT.instr_rom.core);
//  monitor ("dividend=%d,divisor=%d,quotient=%d", dividend, divisor, quotient);
  #10ns start = 1'b0;
  #100ns wait(done);
  #10ns  quotient_DUT = {DUT.data_mem.core[126],DUT.data_mem.core[127]};
  #10ns  $display(quotient_beh,quotient_DUT);
  if(quotient_beh == quotient_DUT)	 // score another successful trial
    score++;

// case 5
  #10ns  start = 1'b1;
  #10ns dividend = 16'h5A5A;
  divisor = 8'h78;
  DUT.data_mem.core[128] = dividend[15:8];
  DUT.data_mem.core[129] = dividend[ 7:0];
  DUT.data_mem.core[130] = divisor;

// clear reg. file -- you may load any constants you wish here     
  for(int i=0; i<16; i++)
	DUT.reg_file1.core[i] = 8'b0;
	DUT.reg_file1.ov = 1'b0;
// load instruction ROM	-- unfilled elements will get x's -- should be harmless
  $readmemb("/home/tommy/Documents/CSE141l/divide/divideMachine.txt",DUT.instr_rom.core);
//  monitor ("dividend=%d,divisor=%d,quotient=%d", dividend, divisor, quotient);
  #10ns start = 1'b0;
  #100ns wait(done);
  #10ns  quotient_DUT = {DUT.data_mem.core[126],DUT.data_mem.core[127]};
  #10ns  $display(quotient_beh,quotient_DUT);
  if(quotient_beh == quotient_DUT)	 // score another successful trial
    score++;

// case 6
  #10ns  start = 1'b1;
  #10ns dividend = 16'h2517;
  divisor = 8'h31;
  DUT.data_mem.core[128] = dividend[15:8];
  DUT.data_mem.core[129] = dividend[ 7:0];
  DUT.data_mem.core[130] = divisor;

// clear reg. file -- you may load any constants you wish here     
  for(int i=0; i<16; i++)
	DUT.reg_file1.core[i] = 8'b0;
	DUT.reg_file1.ov = 1'b0;
// load instruction ROM	-- unfilled elements will get x's -- should be harmless
  $readmemb("/home/tommy/Documents/CSE141l/divide/divideMachine.txt",DUT.instr_rom.core);
//  monitor ("dividend=%d,divisor=%d,quotient=%d", dividend, divisor, quotient);
  #10ns start = 1'b0;
  #100ns wait(done);
  #10ns  quotient_DUT = {DUT.data_mem.core[126],DUT.data_mem.core[127]};
  #10ns  $display(quotient_beh,quotient_DUT);
  if(quotient_beh == quotient_DUT)	 // score another successful trial
    score++;
	 
// case 7
  #10ns  start = 1'b1;
  #10ns dividend = 16'h749;
  divisor = 8'h75;
  DUT.data_mem.core[128] = dividend[15:8];
  DUT.data_mem.core[129] = dividend[ 7:0];
  DUT.data_mem.core[130] = divisor;

// clear reg. file -- you may load any constants you wish here     
  for(int i=0; i<16; i++)
	DUT.reg_file1.core[i] = 8'b0;
	DUT.reg_file1.ov = 1'b0;
// load instruction ROM	-- unfilled elements will get x's -- should be harmless
  $readmemb("/home/tommy/Documents/CSE141l/divide/divideMachine.txt",DUT.instr_rom.core);
//  monitor ("dividend=%d,divisor=%d,quotient=%d", dividend, divisor, quotient);
  #10ns start = 1'b0;
  #100ns wait(done);
  #10ns  quotient_DUT = {DUT.data_mem.core[126],DUT.data_mem.core[127]};
  #10ns  $display(quotient_beh,quotient_DUT);
  if(quotient_beh == quotient_DUT)	 // score another successful trial
    score++;

// case 8
  #10ns  start = 1'b1;
  #10ns dividend = 16'h4fe8;
  divisor = 8'h2e;
  DUT.data_mem.core[128] = dividend[15:8];
  DUT.data_mem.core[129] = dividend[ 7:0];
  DUT.data_mem.core[130] = divisor;

// clear reg. file -- you may load any constants you wish here     
  for(int i=0; i<16; i++)
	DUT.reg_file1.core[i] = 8'b0;
	DUT.reg_file1.ov = 1'b0;
// load instruction ROM	-- unfilled elements will get x's -- should be harmless
  $readmemb("/home/tommy/Documents/CSE141l/divide/divideMachine.txt",DUT.instr_rom.core);
//  monitor ("dividend=%d,divisor=%d,quotient=%d", dividend, divisor, quotient);
  #10ns start = 1'b0;
  #100ns wait(done);
  #10ns  quotient_DUT = {DUT.data_mem.core[126],DUT.data_mem.core[127]};
  #10ns  $display(quotient_beh,quotient_DUT);
  if(quotient_beh == quotient_DUT)	 // score another successful trial
    score++;
	 
// case 9
  #10ns  start = 1'b1;
  #10ns dividend = 16'h72cc;
  divisor = 8'h73;
  DUT.data_mem.core[128] = dividend[15:8];
  DUT.data_mem.core[129] = dividend[ 7:0];
  DUT.data_mem.core[130] = divisor;

// clear reg. file -- you may load any constants you wish here     
  for(int i=0; i<16; i++)
	DUT.reg_file1.core[i] = 8'b0;
	DUT.reg_file1.ov = 1'b0;
// load instruction ROM	-- unfilled elements will get x's -- should be harmless
  $readmemb("/home/tommy/Documents/CSE141l/divide/divideMachine.txt",DUT.instr_rom.core);
//  monitor ("dividend=%d,divisor=%d,quotient=%d", dividend, divisor, quotient);
  #10ns start = 1'b0;
  #100ns wait(done);
  #10ns  quotient_DUT = {DUT.data_mem.core[126],DUT.data_mem.core[127]};
  #10ns  $display(quotient_beh,quotient_DUT);
  if(quotient_beh == quotient_DUT)	 // score another successful trial
    score++;
	 
// case 10
  #10ns  start = 1'b1;
  #10ns dividend = 16'h2801;
  divisor = 8'h72;
  DUT.data_mem.core[128] = dividend[15:8];
  DUT.data_mem.core[129] = dividend[ 7:0];
  DUT.data_mem.core[130] = divisor;

// clear reg. file -- you may load any constants you wish here     
  for(int i=0; i<16; i++)
	DUT.reg_file1.core[i] = 8'b0;
	DUT.reg_file1.ov = 1'b0;
// load instruction ROM	-- unfilled elements will get x's -- should be harmless
  $readmemb("/home/tommy/Documents/CSE141l/divide/divideMachine.txt",DUT.instr_rom.core);
//  monitor ("dividend=%d,divisor=%d,quotient=%d", dividend, divisor, quotient);
  #10ns start = 1'b0;
  #100ns wait(done);
  #10ns  quotient_DUT = {DUT.data_mem.core[126],DUT.data_mem.core[127]};
  #10ns  $display(quotient_beh,quotient_DUT);
  if(quotient_beh == quotient_DUT)	 // score another successful trial
    score++;
	 
	
	#10ns	$stop;
end

always begin
  #5ns  clk = 1'b1;
  #5ns  clk = 1'b0;
end

endmodule
