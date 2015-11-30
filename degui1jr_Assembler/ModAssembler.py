"""
    input: stream of assembly commands
    generates output of binary instructions.
    results can be loaded into computer memory and executed.
    This modified version can handle commands including the B register.


    Pass 1: While input file has more commands, add L_COMMAND to symbol table
    advance rom_counter otherwise

    Pass 2: While input file has more commands, add A_COMMAND to symbol table,
    evaluate binary pieces (and handles B register logic), adds pieces to output string, 
    write the 16 bin string, close file
"""

import parser
import code
import symboltable
import re
import sys, os
import time

count = 0
beginning = time.time()

""" FIRST PASS: Traverse parse and build the symbol table using labels """
FILENAME = os.path.dirname(os.path.realpath(__file__))+"/"+str(sys.argv[1])

try:
    file = parser.Parser(FILENAME)
except:
    print "The file you specified \"" + sys.argv[1] + "\" does not exist."
    sys.exit()

SYMBOL_TABLE = symboltable.SymbolTable()

rom_counter = 0
RAM_ADDRESS = 15

""" if A or C command advance """
while file.has_more_commands():

    file.advance()
    command_type = file.command_type()

    if command_type is 3:
        SYMBOL_TABLE.add_entry(file.symbol(), rom_counter)

    else:
        rom_counter += 1
        

""" SECOND PASS: Traverse parse and build the symbol table using A commands 
    as well as build output file containing the binary equivalent code. """
outputFile = FILENAME.split('.')[0] + '.hack'
Assembler_output = open(outputFile, 'w')
    
file = parser.Parser(FILENAME)
output = ''

while file.has_more_commands():
    
    file.advance()
    convert = code.Code()
    command_type = file.command_type()
    
    if command_type is "A_COMMAND": # if A command
        count += 1
        s = file.symbol()
        if not re.match(r"^[0-9]*$", s): #if symbol, not number
            
            if SYMBOL_TABLE.contains(s): # Check if it exists in table
                b_val = bin(SYMBOL_TABLE.get_address(s))[2:] #s is an integer, make it binary
            
            else:
                RAM_ADDRESS += 1
                SYMBOL_TABLE.add_entry(s, RAM_ADDRESS) #if A and not a number add to table
                b_val=bin(RAM_ADDRESS)[2:]
        
        else: #s is an integer, make it binary
            b_val = bin(int(s))[2:] #s is an integer, make it binary
        output = "0" * (16 - len(b_val)) + b_val
        
        Assembler_output.write(output + "\n")
        
    elif command_type is "C_COMMAND": # if C command
        count += 1

        output = '1'
        if file.dest() is 'B':
            output += '0'
        else:
            output += '1'

        if 'B' in file.comp():
            output += '0'
        else:
            output += '1'
        
        output += str(convert.comp(file.comp())) #converts comp letter(s) into bin value 
        output += str(convert.dest(file.dest())) #converts dest string into bin value 
        output += str(convert.jump(file.jump())) #converts jump letter(s) into bin value
        
        Assembler_output.write(output + "\n")
        
    elif command_type is "L_COMMAND":
        count += 1
        pass # do nothing
    else:
        print "Error on line "+ str(file.counter)

end = time.time()
print count, "command writes in ", end - beginning, " seconds."

Assembler_output.close()