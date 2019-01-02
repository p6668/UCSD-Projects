//This file defines the parameters used in the alu
package definitions;
    
// Instruction map

/*
add
sub
shift right sign extend, uses ov
shift right logical put, uses ov
shift right logical get, uses ov
shift left logical put, uses ov
shift left logical get, uses ov
logical not
sift right immediate, unsigned
no-op
get lower 4 bits
get upper 4 bits
set lower 4 bits
set upper 4 bits
*/
const logic [4:0] kADD	= 0; 
const logic [4:0] kSUB	= 1;
const logic [4:0] kSRSE	= 2;
const logic [4:0] kSRLP	= 3;
const logic [4:0] kSRLG	= 4;
const logic [4:0] kSLLP	= 5;
const logic [4:0] kSLLG	= 6;
const logic [4:0] kNOT	= 7;
const logic [4:0] kSRI	= 8;
const logic [4:0] kNOP	= 9;
const logic [4:0] kGETL	= 10;
const logic [4:0] kGETU	= 11;
const logic [4:0] kINC	= 12;
const logic [4:0] kDEC	= 13;
const logic [4:0] kSLT	= 14;
const logic [4:0] kSETL	= 15;
const logic [4:0] kSETU	= 16;
const logic [4:0] kBRH	= 17;
const logic [4:0] kCLR	= 18;
const logic [4:0] kNOPB	= 19;

	

	
//	typedef enum logic[1:0] {
//		ADDU    = 2'h0, 
//		SUBU    = 2'h1, 
//		AND     = 2'h2,
//		XOR     = 2'h3
//	} op_mne;
//	
	 
endpackage // defintions
