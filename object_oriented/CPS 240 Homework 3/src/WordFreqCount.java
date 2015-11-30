//WordFreqCount.java, John deGuise, Homework 3, 9/30/15
//reads in Sherlock Holmes, and 
//uses a TreeMap to map word existence from Scanner
//sortByValues by instantiating ArrayList with entrySet
//custom Comparator logic sorts values of Array List
//prints top 20 most appeared, and bottom 20 least appeared

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WordFreqCount {

	public static void main(String[] args) {
		try{
			Scanner sc = new Scanner(new File("book.txt"));
			Map<String, Integer> treeMap = new TreeMap<String, Integer>();
			
			//formatting input to exclude punctuation, caps, and numbers
			while(sc.hasNext()){
				String next = sc.next();
				next = next.replaceAll("[^a-zA-Z ]", "").toLowerCase();
								
				//if the key exists, increment the value by 1
				if(treeMap.containsKey(next)) 
					treeMap.put(next, treeMap.get(next) + 1);
				
				//else put it in incremented slot
				else
					treeMap.put(next, 1);
	
			}
			
			sortByValues(treeMap);	
			
			sc.close();

		} 
		
		//exception handling for FNFE
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public static List<Map.Entry<String, Integer>> sortByValues(Map<String, Integer> treeMap){
		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(treeMap.entrySet());
		
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>(){
			
			//comparing values within our custom comparator
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
			
				if(o1.getValue() > o2.getValue()) return 1;
				else if(o1.getValue() < o2.getValue()) return -1;
				else return 0;
			
			}

		});
		
		System.out.println("Top 20 Most Appeared Words:");
		
		//i represents end of list, j is enumeration of printed elements
		for(int i = list.size()-1, j = 1; j <= 20; j++, i--){
			
			//parse value of list.get(i) from Map.Entry<String, Integer> to String
			String value = "" + list.get(i);
			
			//some regex for prettier output
			value = value.replaceAll("=", " --> ");
			
			//top 20 most frequently appeared words
			System.out.println((j + ":") + " " + value);
		
		}	
		
		System.out.println("\nTop 20 Least Appeared Words:");
		
		//i represents enumeration of least occurred values
		//excluding punctuation and numbers
		for(int i = 1; i <= 20; i++){
			
			String value = "" + list.get(i);
			value = value.replaceAll("=", " --> ");
			System.out.println(i + ":" + " " + value);
			
		}
		
		return list;
	}

}
