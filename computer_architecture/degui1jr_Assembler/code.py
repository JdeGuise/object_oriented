"""
    contains dest(), comp(), and jump() methods for linking pieces of binary to respective values
    more or less the big table from our powerpoint notes 

    dest(self, string s): binary code for dest mnemonic; s is the string key that maps to the proper binary value

    comp(self, string s): binary code for comp mnemonic; if there's an M in the string key, a = 1
                    searches for computation in s key, adds and returns the corresponding binary string as result

    jump(self, string s): binary code for jump mnemonic; s is the string key that maps to the proper binary value

"""

import re
class Code:
    list_ops =  [None, 'M', 'D', 'MD', 'A', 'AM', 'AD', 'AMD', 'B']
    def dest(self, s):
        """Returns 3 binary code for dest mnemonic"""        
        return str(bin(self.list_ops.index(s))[2:].zfill(3))[-3:]
        
    def comp(self, s):
        result = ''
        if 'M' in s:
            result += '1'
        else:
            result += '0'
            
        #s = re.sub("B", "D", s) # Everything for D could happen for B
        firstVar = "(B|D)"
        secondVar = "(A|M)"
        plusOneRegex = "\s*\+\s*1"
        minusOneRegex = "\s*-\s*1"

        if "0" == s:
            result += "101010"
        elif "1" == s:
            result += "111111"
        elif "-1" == s:
            result += "111010"
        
        elif re.match(firstVar + "$", s): #(B|D)
            result += "001100"
        elif re.match(secondVar + "$", s): # (A|M)
            result += "110000"
        elif "!D" == s:
            result += "001101"
        elif re.match("!" + secondVar, s): #!(A|M)
            result += "110001"
        elif "-D" == s:
            result += "001111"
        elif re.match("-" + secondVar, s): # - (A|M)
            result += "110011"
        
        elif re.match(firstVar + plusOneRegex, s):  # (B|D)+1
            result += "011111"
        elif re.match(secondVar + plusOneRegex, s): # (A|M) + 1
            result += "110111"
        elif re.match(firstVar + minusOneRegex, s): # (B|D) - 1
            result += "001110"
        elif re.match(secondVar + minusOneRegex, s): # (A|M) - 1
            result += "110010"
        
        elif re.match(firstVar+"\s*\+\s*" + secondVar, s): # (B|D) + (A|M)
            result += "000010"
        elif re.match(secondVar+"\s*\+\s*" + firstVar, s): # (A|M)+(B|D)
            result += "000010"
        elif re.match(firstVar+"\s*-\s*" + secondVar, s):  # (B|D) - (A|M)
            result += "010011"
        elif re.match(secondVar + "\s*-\s*"+firstVar, s):  # (A|M) - (B|D) 
            result += "000111"
        
        elif re.match(firstVar+"\s*&\s*" + secondVar, s):  # (B|D) & (A|M) 
            result += "000000"
        elif re.match(firstVar+"\s*\|\s*" + secondVar, s): # (B|D) | (A|M)
            result += "010101"
        else:
            result = '0000000'
        return result
        
    def jump(self, s):
        """Returns 3 digit binary for jump mnemonic"""
        jump_commands = [None, 'JGT', 'JEQ', 'JGE', 'JLT', 'JNE', 'JLE', 'JMP']
        return str(bin(jump_commands.index(s))[2:].zfill(3))