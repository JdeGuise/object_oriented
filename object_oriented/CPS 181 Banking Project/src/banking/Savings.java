package banking;

import java.io.PrintWriter;

public class Savings extends Bankaccount {

	public Savings(PrintWriter pw, PrintWriter pw2) {
		super(pw, pw2);
		System.out.println("What is the initial balance of the savings account?");
		balance = input.nextDouble();
	}
	
	public static void SavingsCheck(){
		System.out.println(balance);
	}

	public String toString(){ //plus whatever else is needed specific to the savings acct
		return super.toString() + ": savings";
	}
	
	public double addInterest(){ 
		balance += balance/50;
		return balance;
	}
}
