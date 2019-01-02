#!/usr/bin/python2

import sys

inst = ["lw",
        "sw",
        "add",
        "sub",
        "inc",
        "dec",
        "srpx",
        "srp",
        "srg",
        "slp",
        "slg",
        "mov",
        "not",
        "slt",
        "lwl",
        "lwu",
        "getl",
        "getu",
        "setu",
        "setl",
        "addi",
        "sr",
        "bez",
        "bnez",
        "clr",
        "halt"]

# opens valid .lul file prompting from user input
def openFile():
    while True:
        fpath = raw_input("Enter file path: ")
        fpathSplit = fpath.rsplit(".", 1)
        try:
            if fpathSplit[1] == "lul" :
                lulFile = open(fpath, 'r')
                try:
                    lulOut = open(fpathSplit[0]+".txt", 'w')
                    machineOut = open(fpathSplit[0]+"Machine.txt", 'w')
                    return (lulFile, lulOut, machineOut)
                except IOError:
                    print("Error opening output")
            else:
                print("Enter valid .lul file path")
        except (IndexError, IOError):
            print("Enter valid .lul file path")

# scans through document and saves all labels into an array
def getLable(inf):
    lineNum = 0
    labels = []
    labelIndex = []
    for line in inf:
        # get rid of comments
        curr = line.split('//') [0]
        curr = curr.strip()

        # if current line is an instruction
        if curr.find(":") > 0:
            labels.append(curr.rstrip(":"))
            labelIndex.append(lineNum)
        
        elif len(curr) is not 0:
            lineNum += 1

    inf.seek(0)
    print labels
    print labelIndex
    return labels

# scans through document and converts from assemblly to machine code
def decode(inf, outf, mout, labels):
    lineNum = 1

    if len(labels) > 16:
        print("Too many labels! lul supports up to 16 branch labels")
        return;
    
    for line in inf:
        curr = line.split('//') [0]
        curr = curr.strip()

        # if no instruction
        if curr == '':
            continue

        # if label, skip
        if curr.find(":") > 0:
            continue
    
        currInst = curr.split()
        if len(currInst) > 2:
            print("Too many arguments!")
            return
    
        try:
            # get opcode
            opCode = int(inst.index(currInst[0]))

        except ValueError as e:
            print("Illegal instruction at line " + str(lineNum) + " " + curr)
            print(e)
            return

        try:
            # get register
            currReg = currInst[1]

            if 22 <= opCode <= 23:
                try:
                    currReg = labels.index(currReg)
                except ValueError as e:
                    print("Lable " + currReg + " not found!")
                    print(e)
                    return

            # handle immediate
            elif currReg.find('r') == -1:
                if 18 < opCode > 21:
                    print("Instruction is not I-type!: " + currInst[0])
                    return
                if int(currReg) >= 16:
                    print("Immediate greater than 15!")
                    return
                currReg = int(currReg)

            # if register address
            else:
                currReg = int(currReg.rsplit('r', 1) [1])
                if currReg >= 16:
                    print("Invalid register address!")
                    return
        except IndexError as e:
            currReg = 0
    
        outf.write(format(opCode, 'b').zfill(5) + "_" + 
                format(currReg, 'b').zfill(4) + "\t// " + curr + "\n")
        mout.write(format(opCode, 'b').zfill(5) +
                format(currReg, 'b').zfill(4) + '\n')
    
        lineNum += 1


def main():
    inf, outf, mout = openFile()
    labels = getLable(inf)
    getLable(inf)
    decode(inf, outf, mout, labels)
    inf.close()
    outf.close()
    mout.close()

if __name__ =="__main__":
    main()
