"""
        symboltable keeps a corresponding dictionary between symbolic keys and
        numeric values.
      
        add_entry(string symbol as key, int address as value) void
        contains(string symbol) returns bool as 0 or 1
        get_address(string symbol) returns int value corresponding to key

"""

class SymbolTable:
    def __init__(self):
        self.SymbolTable = {'SP':0, 'LCL':1, 'ARG':2,'THIS':3,'THAT':4,'R0':0,'R1':1,'R2':2,'R3':3,'R4':4,'R5':5,
                            'R6':6,'R7':7,'R8':8,'R9':9,'R10':10,'R11':11,'R12':12,'R13':13,'R14':14,'R15':15,'SCREEN':16384,'KBD':24576}
            
    def add_entry(self, symbol, address):
        self.SymbolTable[symbol] = address
        
    def get_address(self, symbol):
        return self.SymbolTable[symbol]

    def contains(self, symbol):
        return self.SymbolTable.__contains__(symbol)
        
        