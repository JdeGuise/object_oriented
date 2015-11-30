//MyUtil.java, John deGuise, Homework 5, 10/16/15

import java.util.List;


public class MyUtil {
	
	//generic swap
	private static <E> void swap(List<E> a, int i, int j){
		
		//swap indexes i and j of E[] a
		if (i != j){
			E temp = a.get(j);
			a.set(j, a.get(i));
			a.set(i, temp);
		}
		
	}
	
	//generic selectionsort
	public static <E extends Comparable<E>> void selectionSort(List<E> a){ 
	
		int mIndex;
		//first for, iterating the entire length of a-1
		for (int i = 0; i < a.size() - 1; i++){
			mIndex = i;
					
			//second for, finding minimum values to the right of mIndex and comparing to mIndex
			for (int j = i + 1; j < a.size(); j++){
				if(a.get(mIndex).compareTo(a.get(j)) > 0){
						
					//if it finds a smaller element, reassigns mIndex
					mIndex = j;
					
				}
			}
							
			//swap values and sort ascending based on selectionSort
			swap(a, mIndex, i);			
		}
	
	}
}
