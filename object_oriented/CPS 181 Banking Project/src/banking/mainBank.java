//John deGuise - CPS181 T/TH 9:30

package banking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class mainBank {

	public static void main(String[] args) throws FileNotFoundException {
		LinkedList<Bankaccount> list = new LinkedList<Bankaccount>();		
		File file = new File ("Output.txt");
		PrintWriter output = new PrintWriter(file);

		File bankPeople = new File("bankpeople.txt");
		File bankFile = new File("bankfiles.txt");
		
		PrintWriter pw = new PrintWriter(bankFile);
		PrintWriter pw2 = new PrintWriter(bankPeople);

		Checking person1 = new Checking(pw, pw2);
		Savings person2 = new Savings(pw, pw2);
		
		Bankaccount person3 = new Checking(pw, pw2);
		person3 = new Savings(pw, pw2);
		
		list.add(person1);
		output.println(person1);
		list.add(person2);
		output.println(person2);
		list.add(person3);
		output.println(person3);
		
		for(Bankaccount b : list){
			if(b instanceof Savings){
				((Savings) b).addInterest(); //for bankaccounts in list, if the bank account is a Savings, add the interest
			}
		}
		
		output.close();
		pw2.close();
	}

}
