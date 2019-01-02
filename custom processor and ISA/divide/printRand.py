#!/usr/bin/python

import math
import random

def randDivide():
    x = random.randrange(math.pow(2,8)-1)
    y = random.randrange(math.pow(2,16)-1)
    print hex(y), "/", hex(x), " = ", hex(y/x)

for i in range(0,5):
    randDivide()
