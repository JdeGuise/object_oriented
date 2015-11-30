//IME.java, John deGuise, Homework 2, 9/16/15		

//Program reads in two separate integers, and if they're valued as anything other than an integer, caught by IME and reprompted
//if both a and b are equal to an (int) cast of the variable, then they're not truncating, so they're equal - break the loop
//print sum of ints a and b

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class IME {

	public static void main(String[] args) {
		int a = 0, b = 0;
		boolean intValues = true;
		
		File f = new File("HW2.txt");						//Create new output file with IOException
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Please enter two separate integers.");
		while(intValues){											//while loop until the correct values are in a and b
			
			try{
				a = sc.nextInt();									//make a the next int - may trigger catch

				b = sc.nextInt();		
				
				break;
			}
			
			catch(InputMismatchException ime){
				
				System.out.println("Input Mismatch.  Please try again with two seperate integers.");
				ime.printStackTrace();								//print stacktrace
				sc.nextLine();											//prompt for next input
				continue;											//push to next condition of while loop
			
			}

		}
		
		System.out.println("Sum of " + a + " and " + b + " is " + (a+b) + "\n");		//prompt with summed answer
			
		sc.close();													//close scanner
	
	}

}
