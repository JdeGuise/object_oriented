import java.util.ArrayList;

public class Search<T extends Comparable<T>> {
	private ArrayList<T> a;		//for homework condition testing
	int counter = 1;
	
	public Search(ArrayList<T> al){ //public Search constructor passing generic in ArrayList
		a = al;
	}

	public Object BinarySearch(Comparable<T> v){ //BinarySearch with generic parameter returns Object
		int low = 0;
		int high = a.size() - 1;
		int mid;
		
		int returnValue = 0;
		
		while(low <= high){
			System.out.println("Pass #" + counter);
			mid = (low + high) / 2;
			T midVal = a.get(mid); //generic midVal representation = arrayList.get(mid) which gets the value of a[mid] in arrayList terms
			
			if(v.compareTo(midVal) == 0){ //if '7555' compares perfectly to midVal(the value of what we're focused on), it == 0
				returnValue = mid; //return value is the index of where mid is currently. this is our return value
				System.out.println(v + " found at index " + mid + ". Took " + counter + " passes to determine this.");
				break;
			}
			
			else if(v.compareTo(midVal) < 0){ //guess was too high, new high is below the current mid
 				high = mid - 1;
			}
			
			else{ //guess was too low, new low is above the current mid
				low = mid + 1;
			}
			
			if( low > high ){ //if the values increment beyond making sense, the value isn't in the list.
				System.out.println(v + " is not present in the list.\n Took " + counter + " passes to determine this.");
				returnValue = -1; //return -1 and break
				break;
			}
			counter++;
		}
		
		return returnValue;
	
	}
	
}
