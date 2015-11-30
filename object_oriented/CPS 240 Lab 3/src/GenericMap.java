import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GenericMap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Map<String, Double> grades = new TreeMap<String, Double>();
		grades.put("John",  22.0);
		grades.put("Kevin",  100.0);
		grades.put("Jon",  105.0);
		
		Map<Integer, String> students = new TreeMap<Integer, String>();
		students.put(534830, "degui1jr");
		students.put(1033,  "dries1nb");
		
		System.out.println("Max: " + max(grades));
		System.out.println("Sort by keys: " + grades);
		System.out.println("Sort by values: " + sortByValue(grades));
		
		
	}

	public static <K, V extends Comparable<V>> V max(Map<K,V> map){
		V max = null;
		
		for(K key: map.keySet()){
			
			V value = map.get(key);
			
			if(max == null)
				max = value;
			
			else if(value.compareTo(max) > 0)
				max = value;
		
		}
		
		return max;

	}
	
	public static <K, V extends Comparable<V>> List<Map.Entry<K, V>> sortByValue(Map<K, V> map){
		
		List<Map.Entry<K, V>> list = new ArrayList<Map.Entry<K, V>>(map.entrySet());
		
		Collections.sort(list, new Comparator<Map.Entry<K, V>>(){

			@Override
			public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
				return e1.getValue().compareTo(e2.getValue());
			}
			
		});
		
		return list;
	
	}

}
