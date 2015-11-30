//@Author: John deGuise CPS181 Michael Stinson due 3/26/15

import java.util.*;

public class Search {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int index;
		ArrayList<Integer> al = new ArrayList<Integer>();
		
		
		/*Creating 10000 random objects and adding them to the ArrayList */
		for(int i = 0; i < 10000; i++){ 
			int temp = (int)(Math.random()*250000);
			System.out.println(temp);
			al.add(temp);
		}
		
		System.out.println("Done randomizing values");
		System.out.println(al.size()); //should be 10000
		
		Collections.sort(al); //sorting ArrayList 
		System.out.println(al); //showing sorted ArrayList
		
		index = BinarySearch(al, 7555); /*calling BinarySearch method 
		 							which returns a positive index if the key is found
		 							and stores in index*/
		
		if(index >= 0){
			System.out.println(index); //if it's positive, it was found and is displayed
		}
		
		else{
			System.out.println("Number not found."); //else, it wasn't and prompts as such
		}
		
	}
	
	public static int BinarySearch(ArrayList<Integer> arraylist, int key){
		int last = (arraylist.size() - 1);
		int first = 0;
		int middle = (first + last) / 2;
		int counter = 0; //comparison incrementer
		int returnValue = 0; //local return variable for logic 
		
		while(first <= last){ //while there's still reasonable values to check, essentially
			if(arraylist.get(middle) == key){ //if it finds it, set returnValue properly and break
				System.out.println(key + " found at index location " + (middle + 1) + ".");
				returnValue = middle+1;
				break;
			}
			
			else if(arraylist.get(middle) < key){ //else, it's too low, so it moves the first number
				first = middle + 1;
			}
			else {
				last = middle - 1; //else else, it's too high, so it moves the last number
			}
			
			middle = (first + last)/2; //resets the middle to wherever it should be
			
			counter++; //increments our comparison counter and displays
			System.out.println("Total comparisons made: " + counter + "."); 
			
			if ( first > last ){ //if there's no values left to check, set the returnValue properly and break
				System.out.println(key + " is not present in the list.\n");
				returnValue = -1;
				break;
			}
		}
		
		return returnValue; //return whether we found it or not
		
	}
}
