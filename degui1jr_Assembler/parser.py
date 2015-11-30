"""input: assembly language commands, parses
    provides access to components like symbols
    removes white space/comments.

    __init__(file) prepares input file for reading and parses comments/whitespace, void

    dest(self) returns dest mnemonic for specifically C_COMMAND

    comp(self) returns comp mnemonic for specifically C_COMMAND, based on searching for identifiers = and ;

    jump(self) returns jump mnemonic for specifically C_COMMAND



"""    

import re

class Parser:
    
    def __init__(self, file):

        self.counter = 0
        self.current = None
        self.commands = []

        input_txt = open(file, 'r')
        for line in input_txt:
         
           """Check if comment, check if blank, if neither append to commands"""
          
           if not re.match("//", line.strip()) and not len(line.strip()) == 0:
               line=line.strip()
               lineSplit = line.split(' ', 1)
               self.commands.append(lineSplit[0])

    def dest(self):
        
        """Returns dest mnemonic in the current C_COMMAND, only called if commandType is C_COMMAND"""
        # if finds =, returns proper piece of dest
        
        current = self.current
        sel = current.find("=")
        if sel > -1:
            return current[:sel]
        return None
        
    def comp(self):
        
        """Returns comp mnemonic in the current C_COMMAND, only called if commandType is C_COMMAND"""
        
        current = self.current
        sel = current.find(";") # is a jmp comp
        
        if sel > -1:
            return current[:sel] # grabs the D in comp mnemonic
        sel = current.find("=") # assignment to comp
        if sel > -1:
            return current[sel+1:]#Grab everything after =
        return None
        
    
    def jump(self):
       
        """Returns jump mnemonic in the current C_COMMAND, only called if command_type is C_COMMAND"""
        
        jump_commands = ['JGT', 'JEQ', 'JGE', 'JLT', 'JNE', 'JLE', 'JMP']
        current = self.current
        
        sel = current.find(";")
        current = current[sel+1 :]
        
        if current in jump_commands: #if it finds a ;, then looks in jump commands and returns
            return current
        else:
            return None           
    

    def advance(self):
        
         """Reads next command from input and makes it the current command"""
         
         self.current = self.commands[self.counter]
         self.counter += 1

    def has_more_commands(self):
        
        """Returns 1 if there are more commands, 0 otherwise"""
        
        return self.counter + 1 <= self.commands.__len__()
        

         
    def command_type(self):
        
        """Checks the command type, returns A = 1, C = 2, L = 3"""
        
        cmd = self.current
        command_types = {"A": "A_COMMAND", "C": "C_COMMAND", "L": "L_COMMAND"}

        if cmd[0] is "@":
            return command_types.get("A")

        if cmd[0] is "(":
            return command_types.get("L")

        else:
            return command_types.get("C")
    
    def symbol(self):
        
        """Called only when command_type is A_COMMAND OR L_COMMAND, removes () and @"""
        
        command_type = self.command_type() #checks commandtype
        
        if command_type is "A_COMMAND" or command_type is "C_COMMAND": #A or L command, parsing input
             return self.current.replace("@", "").replace("(", "").replace(")", "")
             

        
    
    