package banking;

import java.io.PrintWriter;

public class Checking extends Bankaccount{

	public Checking(PrintWriter pw, PrintWriter pw2) {
		super(pw, pw2);
		System.out.println("What is the initial balance of the checking account?");
		balance = input.nextDouble();
		
	}
	
	public static void Check(){
		System.out.println(balance);
	}
	
	public String toString(){ //plus whatever else is needed specific to the savings acct
	
		
		return super.toString() + ":checking ";
		
	}
	
	

}
