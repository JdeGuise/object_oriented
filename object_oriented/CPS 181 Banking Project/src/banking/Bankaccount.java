package banking;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.PrintWriter;

public class Bankaccount {
	Scanner input = new Scanner(System.in);

	private String firstName;
	private String address;
	private int pin;
	static double balance;
		
	public String getfirstName(PrintWriter pw) {
		pw.println(firstName);
		return firstName;
	}
		
	public String getAddress(){
		return address;
	}

	public boolean verifyPin() {
		System.out.println("Please verify your pin.");
		int tempPin = input.nextInt();
		if(tempPin == pin){
			return true;
		}
		else{
			System.out.println("Access Denied");
			return false;
		}
	}
	
	public void getBalance() {	
		
		if(verifyPin() == true){
			System.out.println("$" + this.balance);
		}
		
		else{
				System.out.print("Access denied");
			}		
	}

	public void setBalance(PrintWriter pw2) {
		System.out.println("How much would you like to deposit?");
		double amount = input.nextDouble();
			
		System.out.println("$" + this.balance + " will be deposited into your bank account.");
		this.balance += amount;
			
		System.out.println("Your total balance is now $" + this.balance + ". Have a good day!");
		pw2.println(toString() + ": Deposit");
		pw2.close();
	}
		
//	public void accountWithdraw(PrintWriter pw2){
//		if(verifyPin() == true){
//			System.out.println("How much would you like to withdraw?");
//			double amount = input.nextDouble();
//				
//			if(amount > balance){
//				System.out.println("Insufficient funds. Access denied.");
//			}
//				
//			if(amount <= balance){
//				System.out.println("$" + amount + " will be withdrawn from your bank account.");
//				this.balance -= amount;
//				System.out.println("Your total balance is now $" + this.balance + ". Have a good day!");
//				pw2.println(toString() + ": Withdrawl");
//				pw2.close();
//			}	
//		}
//		else{
//			System.out.println("Access denied.");
//		}
//	}
		
	public Bankaccount(PrintWriter pw, PrintWriter pw2){
		System.out.println("Welcome to Generic Bank Account Setup! What is your first name?");
		firstName = input.nextLine();
		System.out.println("What is your address");
		address = input.nextLine();
		System.out.println("Please create your starting pin.");
		
		try{
			pin = input.nextInt();
		}
		catch (InputMismatchException nonint){
			System.out.println("Non-integer input.  Please try again with a 4 digit number.");
			pin = input.nextInt();
		}
		finally{
			System.out.println("Welcome to Generic Bank, " + firstName + " " + "! Your address is " + address);
		}
		
//		while (!(balance > 5)){
//			try{
//				System.out.println("All bank accounts require a minimum of five dollars.");
//				System.out.println("What is the beginning balance?");
//				balance = input.nextDouble();
//			}
//			catch (InputMismatchException error){
//				System.out.println("Non-numerical input.  Please try again.");
//				balance = input.nextDouble();
//			}
//			finally{
//				System.out.println("Your total balance is now $" + balance + ". Have a good day!");
//				pw.println(firstName);
//				pw2.println(toString() + ": Initial Balance");
//				pw2.close();
//			}
//		}
	}

	public String datetoString(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date); //2014/08/06 15:59:48
	}
		
	public String toString(){
		return firstName + " " + " $" + this.balance + " " + datetoString();
	}

}
