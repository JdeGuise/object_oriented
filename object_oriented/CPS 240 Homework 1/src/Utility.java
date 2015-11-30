//Utility.java, John deGuise, Homework1b, 9/9/15
//Selection Sort implementation with an arraylist of 100 random items (values 0-99)
//RemoveDuplicates takes List a, moves values to a set, and then back to an a list

import java.util.*;

public class Utility {
		
	public static List<Integer> selectionSort(List<Integer> a){ 
		int i, j, min, temp, tempIndex = 0;
		int n = (a.size() - 1);
		
		//first for, setting the min value/index as it works through the elements
		for (i = 0; i < n; i++){
			min = a.get(i);
			tempIndex = i;
				
			//second for, setting values and index to the right of i
			for (j = i; j <= n; j++){
				
				//if min > the value at 'a[j]'
				if(min > a.get(j)){
					
					//reassign to the smaller value and index
					min = a.get(j);
					tempIndex = j;
				
				}
			
			}
				
			if (tempIndex == i);			//index is lowest number, do nothing this pass
			
			else{
				
				temp = a.get(i);			//temp = 'a[i]' where i is the leftmost active index
				a.set(i, a.get(tempIndex)); //set new minimum value into i
				a.set(tempIndex, temp);		//replace minimum's old spot with i's old value
			
			}				
		}
		
		return a;
	
	}
	
	public static List<Integer> removeDuplicates(List<Integer> a){
		//input List of Integers a
		//output: either void or updated List<Integer> with no duplicates
		Set<Integer> set = new HashSet<Integer>(a);							//Instantiate hashset with a 
																			//- eliminates duplicates
		List<Integer> list = new ArrayList<Integer>(set);					//Put back into list and return
		
		return list;
	}
		
	public static void main(String[] args) {
		//initializing list as ArrayList<Integer>
		List<Integer> list = new ArrayList<Integer>();
		
		//bridging the int cast from Math.random with Integer
		Integer test;
		
		//creating the list to sort
		for(int i = 0; i < 100; i++){
			test = (int)(Math.random()*100);
			list.add(test);
		}
		
		System.out.println(list + "\n");
		
		//sorts list in ascending order
		selectionSort(list);
		System.out.println(list + "\n");
		
	}
	
}
