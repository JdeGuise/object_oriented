//@Author: John deGuise CPS181 Michael Stinson HW 7 Revision 

import java.util.*;

public class SearchMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Integer> al = new ArrayList<Integer>(); 

		/*Creating 10000 random objects and adding them to the ArrayList */
		for(int i = 0; i < 10000; i++){ 
			int temp = (int)(Math.random()*250000); //temp = int typecast of Math.random for values through 250000
			System.out.println(temp); //prints out the value to be added to the array list
			al.add(temp); //add value to array list al
		}
		
		al.add(7555); //for testing purposes (chance that value is actually in list is pretty low)
		
		System.out.println("Done randomizing values");
		System.out.println(al.size()); //should be 10000 or 10001 (depending if al.add is uncommented and if 7555 generated naturally
		
		Collections.sort(al); //sorting ArrayList 
		System.out.println(al); //showing sorted ArrayList
		
		Search<Integer> searcher = new Search<Integer>(al); //constructs searcher from Search class and passes ArrayList al 
		
		System.out.println(searcher.BinarySearch(7555)); //print method call of BinarySearch on 7555.  
	}

}
