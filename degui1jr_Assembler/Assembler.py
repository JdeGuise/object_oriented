""" 
    input: stream of assembly commands
    generates output of binary instructions.
    results can be loaded into computer memory and executed. 

    Pass1: while input file has more commands, add L_Commands to symbol table
    advance rom_counter otherwise

    Pass2: while input file has more commands, add A_Commands to symbol table,
    evaluate binary pieces, add them to output string, write the 16bin string,
    close file
"""

import parser
import code
import symboltable
import re
import sys, os
import time
beginning = time.time()
count = 0


""" FIRST PASS: Traverse parse, fill SYMBOL_TABLE with labels """
FILENAME = os.path.dirname(os.path.realpath(__file__))+"/"+str(sys.argv[1])

try:
    file = parser.Parser(FILENAME)

except:
    print "File \"" + sys.argv[1] + "\" does not exist."
    sys.exit()

SYMBOL_TABLE = symboltable.SymbolTable()

rom_counter = 0
RAM_ADDRESS = 15

""" if A or C command advance, if L add to table """
while file.has_more_commands():
    
    file.advance()
    command_type = file.command_type()
    
    if command_type is "L_COMMAND":
        SYMBOL_TABLE.add_entry(file.symbol(), rom_counter)
    
    else:
        rom_counter += 1
        

""" SECOND PASS: Traverse parse, fill SYMBOL_TABLE using A commands, 
    fill output file containing the binary equivalents."""

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
        if not re.match(r"^[0-9]*$", s): #if s is a symbol (non numerical)
            
            if SYMBOL_TABLE.contains(s): # Check if it exists in table
                b_val = bin(SYMBOL_TABLE.get_address(s))[2:] #symb is an integer, make it binary
            
            else: #not in table, so add it.
                RAM_ADDRESS += 1
                SYMBOL_TABLE.add_entry(s, RAM_ADDRESS) #if A and not a number add to table
                b_val = bin(RAM_ADDRESS)[2:]
                
        else: #s is integer, -> binary
            b_val = bin(int(s))[2:]
        output = "0" * (16 - len(b_val)) + b_val #piecing the A command together

        Assembler_output.write(output + "\n")
        
    elif command_type is "C_COMMAND": # if C command
        count += 1
        output = '111'
        
        output += str(convert.comp(file.comp())) #converts comp letter(s) into bin value 
        output += str(convert.dest(file.dest())) #converts dest string into bin value 
        output += str(convert.jump(file.jump())) #converts jump letter(s) into bin value
        
        Assembler_output.write(output + "\n")       #write out the 16 char string output and a new line
        
    elif command_type is "L_COMMAND": # if L command
        count += 1
        pass # do nothing
    else:
        print "Error on line "+ str(file.counter)

end = time.time()
print count, "command writes in ", end - beginning, " seconds."

Assembler_output.close()