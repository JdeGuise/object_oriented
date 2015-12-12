import sys

# Script for managing the work of my student painting company for Summer of 2016
class Employee(object):
	def __init__(self, name, wage):
		self.name = name
		self.wage = wage

	def get_name(self):
		return self.name

	def get_wage(self):
		return self.wage

class HourlyEmployee(Employee):
	def __init__(self, manager, title, dept, ending_bonus, *args):
		self._manager = manager
		self._title = title
		self._dept = dept
		self._ending_bonus = ending_bonus
		super(HourlyEmployee, self).__init__(*args)

	def get_name(self):
		return "Employee name: " + Employee.get_name(self)
	
	def get_wage(self):
		return Employee.get_name(self) + "'s base wage: " + (str)(Employee.get_wage(self))

	def get_manager(self):
		return Employee.get_name(self) + "'s manager: " + self._manager

	def get_title(self):
		return Employee.get_name(self) + "'s title: " + self._title

	def get_dept(self):
		return Employee.get_name(self) + "'s department name: " + self._dept

	def get_ending_bonus(self):
		return Employee.get_name(self) + "'s ending bonus is: " + (str)(self._ending_bonus)

class Executive(Employee):
	# wage = 39% of margin on gross revenue (sales rev. - COGS - labor expense * .39)
	def __init__(self, *args):
		# this will have to be changed to reference the numbers used in the account manager (39% gross revenue of each account)
		super(Executive, self).__init__(*args)

	def get_exec_name(self):
		return "Executive name: " + Employee.get_name(self)

	def get_exec_wage(self):
		return Employee.get_name(self) + "'s wage represents the 39% taken by YEAA = " + (str)(Employee.get_wage(self))

class Manager(Employee):
	# wage = 61% of margin on gross revenue (sales rev. - COGS - labor expense * .61)
	def __init__(self, *args):
		# this will have to be changed to reference the numbers used in the account manager (61% gross revenue)
		super(Manager, self).__init__(*args)

	def get_mgr_name(self):
		return "Branch Manager name: " + Employee.get_name(self)

	# this will have to be changed to reference the numbers used in the account manager
	def get_mgr_wage(self):
		return Employee.get_name(self) + "'s wage represents (gross revenue - 39% taken by YEAA) = " + (str)(Employee.get_wage(self))

# 10000 is a placeholder for the 61% of margin that will be left after the YEAA subtraction
john = Manager("John deGuise", 10000)

# 1000 is a placeholder for ending bonus.  ending bonus is my choice and a tbd calculation
# wages also placeholders
mason = HourlyEmployee("John deGuise", "Painter", "Production", 1000, "Mason Seibert", 12.0)
ryan = HourlyEmployee("John deGuise", "Painter", "Production", 1000, "Ryan Sinclair", 14.0)
andrew = HourlyEmployee("John deGuise", "Painter", "Production", 1000, "Andrew March", 10.0)

# 20000 is a placeholder for the 39% of margin that YEAA will end up taking
matt = Executive("Matt Buhl", 20000)

if __name__ == '__main__':
	sys.exit(0)
