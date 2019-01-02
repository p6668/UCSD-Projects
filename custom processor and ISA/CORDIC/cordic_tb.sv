module cordic_tb;

bit  signed [11:0] x;
bit  signed [11:0] y;
// bit  signed [7: 0] reg1,reg2,reg3,reg4,reg5,reg6,reg7,reg8,reg9,reg10,reg11,reg12,reg13,reg14,reg15,reg0,mem0,mem1,mem2,mem3,mem8,mem9; //tested
wire signed [15:0] r_beh;
wire [11:0] t_beh; 
logic signed [15:0] r_DUT;
logic [11:0] t_DUT; 
bit          clk, start;
wire         done;
bit  [  7:0] score;            // how many correct trials

// be sure to substitute the name of your toplevel module here
// also, substitute names of your ports, as needed
toplevel DUT(
  .CLK   (clk),
  .start (start),
  .halt  (done)
  );

// behavioral model to match
cordic DUT1(
	x,y,r_beh,t_beh
   );

initial begin
  #10ns  start = 1'b1;
  #10ns for (int i=0; i<256; i++)		 // clear data memory
	      DUT.data_mem.core[i] = 8'b0;
// you may preload any desired constants into your data_mem here
//    ...
// case 1
  x = 16'h100;
  y = 16'h100;
  DUT.data_mem.core[0] = 8'h10;
  DUT.data_mem.core[1] = 8'h00;
  DUT.data_mem.core[2] = 8'h10;
  DUT.data_mem.core[3] = 8'h00;

// clear reg. file -- you may load any constants you wish here     
  for(int i=0; i<16; i++)
	DUT.reg_file1.core[i] = 8'b0;
	
	DUT.reg_file1.ov = 1'b0;//clear overflow too
// load instruction ROM	-- unfilled elements will get x's -- should be harmless
  $readmemb("D:\\Lab2\\instruct.txt",DUT.instr_rom.core);
//  monitor ("x%d,y=%d,r=%f, t=%f", x,y,r_beh*0.6074/16.0,lookup[t_beh]);
  #10ns start = 1'b0;
  #100ns wait(done);
  #10ns  r_DUT = {DUT.data_mem.core[5],DUT.data_mem.core[6]};
         t_DUT = {DUT.data_mem.core[7],DUT.data_mem.core[8][7:4]};
  #10ns  $display(r_beh,,,t_beh,,,,,r_DUT,,,t_DUT);
  if(r_beh == r_DUT && t_beh == t_DUT)	 // score another successful trial
    score++;

 //case 2
  #10ns  start = 1'b1;
  #10ns x = 16'h100;
  y = 16'h50;
  
  DUT.data_mem.core[0] = 8'h10;
  DUT.data_mem.core[1] = 8'h0;
  DUT.data_mem.core[2] = 8'h5;
  DUT.data_mem.core[3] = 8'h0;


// clear reg. file -- you may load any constants you wish here     
  for(int i=0; i<16; i++)
	DUT.reg_file1.core[i] = 8'b0;
	
	DUT.reg_file1.ov = 1'b0;//clear overflow too
// load instruction ROM	-- unfilled elements will get x's -- should be harmless
  $readmemb("D:\\Lab2\\instruct.txt",DUT.instr_rom.core);
//  monitor ("x%d,y=%d,r=%f, t=%f", x,y,r_beh*0.6074/16.0,lookup[t_beh]);
  #10ns start = 1'b0;
  #100ns wait(done);
  #10ns  r_DUT = {DUT.data_mem.core[5],DUT.data_mem.core[6]};
         t_DUT = {DUT.data_mem.core[7],DUT.data_mem.core[8][7:4]};
  #10ns  $display(r_beh,t_beh,r_DUT,t_DUT);
  if(r_beh == r_DUT && t_beh == t_DUT)	 // score another successful trial
    score++;



// case 3
  #10ns  start = 1'b1;
  #10ns x = 16'h50;
  y = 16'h100;
  DUT.data_mem.core[0] = 8'h5;
  DUT.data_mem.core[1] = 8'h0;
  DUT.data_mem.core[2] = 8'h10;
  DUT.data_mem.core[3] = 8'h0;

// clear reg. file -- you may load any constants you wish here     
  for(int i=0; i<16; i++)
	DUT.reg_file1.core[i] = 8'b0;
	
	DUT.reg_file1.ov = 1'b0;//clear overflow too
// load instruction ROM	-- unfilled elements will get x's -- should be harmless
  $readmemb("D:\\Lab2\\instruct.txt",DUT.instr_rom.core);
//  monitor ("x%d,y=%d,r=%f, t=%f", x,y,r_beh*0.6074/16.0,lookup[t_beh]);
  #10ns start = 1'b0;
  #100ns wait(done);
  #10ns  r_DUT = {DUT.data_mem.core[5],DUT.data_mem.core[6]};
         t_DUT = {DUT.data_mem.core[7],DUT.data_mem.core[8][7:4]};
  #10ns  $display(r_beh,t_beh,r_DUT,t_DUT);
  if(r_beh == r_DUT && t_beh == t_DUT)	 // score another successful trial
    score++;

	// case 4
  #10ns  start = 1'b1;
  #10ns x = 16'h100;
  y = 0;
  DUT.data_mem.core[0] = 8'h10;
  DUT.data_mem.core[1] = 8'h0;
  DUT.data_mem.core[2] = 8'h0;
  DUT.data_mem.core[3] = 8'h0;

// clear reg. file -- you may load any constants you wish here     
  for(int i=0; i<16; i++)
	DUT.reg_file1.core[i] = 8'b0;
	
	DUT.reg_file1.ov = 1'b0;//clear overflow too
// load instruction ROM	-- unfilled elements will get x's -- should be harmless
  $readmemb("D:\\Lab2\\instruct.txt",DUT.instr_rom.core);
//  monitor ("x%d,y=%d,r=%f, t=%f", x,y,r_beh*0.6074/16.0,lookup[t_beh]);
  #10ns start = 1'b0;
  #100ns wait(done);
  #10ns  r_DUT = {DUT.data_mem.core[5],DUT.data_mem.core[6]};
         t_DUT = {DUT.data_mem.core[7],DUT.data_mem.core[8][7:4]};
  #10ns  $display(r_beh,t_beh,r_DUT,t_DUT);
  if(r_beh == r_DUT && t_beh == t_DUT)	 // score another successful trial
    score++;

	
	#10ns	$stop;
end

always begin
  #5ns  clk = 1'b1;
  #5ns  clk = 1'b0;
end

endmodule
