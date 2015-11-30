//Test.java, John deGuise, Homework1, 9/9/15		

//Uses genList method to create a List of n random ints between [n, 2n],
//creates a file to output (using PrintWriter), starts the stopwatch, generates 3 lists n=(100),(1000),(100000), and selectionSorts
//outputs elapsed stopwatch time before list generation, after list generation, and after the selection sort of each list

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;

public class Test {

	public static List<Integer> genList(int n){
		Integer test; //bridging the int cast for Math.random with List<Integer>
		
		List<Integer> list = new ArrayList<Integer>(n);

		for(int i = 0; i < n; i++){										//adding one element per iteration
			test = (int)(Math.random()*(2*n));			//test is an int cast of Math.random [n, 2n]
			list.add(test);										//adding one element
		}
		
		return list;											//return List object
	}
	
	
	public static void main(String[] args){
		int list1Size = 100;
		int list2Size = 100000;
		StopWatch.start();										

		List one = genList(list1Size);										//List object 1 (n=100)
		System.out.println("Generated list with 100 random items: " + StopWatch.getElapsedTime() + " seconds elapsed.");

		one = Utility.selectionSort(one);								//selectionSort list one
		
		System.out.println("Selection sort complete: " + StopWatch.getElapsedTime() + " seconds elapsed.");	
		System.out.println("List before removing duplicates: \n" + one);

		one = Utility.removeDuplicates(one);
		
		System.out.println("List after removing duplicates: \n" + one);
		System.out.println(StopWatch.getElapsedTime() + " seconds elapsed.");
		System.out.println("List size before / after removal: " + list1Size + " / " + one.size());
		
		
		List two = genList(list2Size);										//List object 1 (n=100)
		System.out.println("\nGenerated list with 100000 random items: " + StopWatch.getElapsedTime() + " elapsed.");

		two = Utility.selectionSort(two);								//selectionSort list one
		
		System.out.println("Selection sort complete: " + StopWatch.getElapsedTime() + " seconds elapsed.");	

		two = Utility.removeDuplicates(two);
		
		System.out.println("Removed duplicates: " + StopWatch.getElapsedTime() + " seconds elapsed.");
		System.out.println("List size before / after removal: " + list2Size + " / " + two.size());
		
		StopWatch.stop();
		
	}

}
