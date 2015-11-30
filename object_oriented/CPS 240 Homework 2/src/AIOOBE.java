//AIOOBE.java, John deGuise, Homework 2, 9/16/15		
//program fills an array of 100 ints, uses scanner to prompt user to pick an index, returns correlating value

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AIOOBE {

	public static void main(String[] args) {
		int array[] = new int[100];							//initialize array
		int index = 0;										
		boolean intValues = true;
		
		File f = new File("HW2.txt");						//Create new output file with IOException
		Scanner input = new Scanner(System.in);				
		
		for(int i = 0; i < 100; i++){									//fill array with 100 random values
			array[i] = (int)(Math.random()*(10000));
			System.out.println(i + " " + array[i]);			//display correlating indices and values
		}
		
		System.out.println("Enter the desired index of the array as an integer.");

		while(intValues){											//while loop until the correct values are in index

			try{
				
				index = input.nextInt();									//make input the next int - may trigger catch
				
				System.out.println("The element associated with index " + index + " is " + array[index]);

				break;
			
			}
			
			catch(ArrayIndexOutOfBoundsException aioob){		
				
				System.out.println("User input does not match criteria. Exception " + aioob);
				
				System.out.println("Please reselect an index.\n");		//while loop continues on to next int

			}
			catch(InputMismatchException ime){
				
				System.out.println("User input does not match criteria. Exception " + ime);
				
				System.out.println("Please reselect an index.\n");
				
				input.nextLine();				//this input.next is here because the scanner value that triggered the IME
											//is no longer an int.  We have to call new values from scanner in this catch
											//or it will trigger ime an exception outside of the try (on the while)

			}

			
		}
		
	}

}
