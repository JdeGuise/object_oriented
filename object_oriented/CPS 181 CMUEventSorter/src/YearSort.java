import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class YearSort {

	public static void main(String[] args) throws IOException {
		//inputFile is the events.txt file
		File inputFile = new File("events.txt");
		//create a Scanner based on the inputFile
		Scanner inFile = new Scanner(inputFile);
		//initialize ArrayList years as new ArrayList
		ArrayList<String> years = new ArrayList<String>();
		//declare/initialize whether or not 1892 is in the list (CMU Historic Event)
		boolean isHistoricEvent = false;
		
		//While inFile has information, adds the inFile line to years and trims the white space
		while(inFile.hasNext()) {
			years.add(inFile.nextLine().trim());
		}
		
		//Reverses the list of years
		Collections.reverse(years);
		//prints out the reversed list
		System.out.println(years);
		
		//if element i in the years array == 1892, set isHistoricEvent to true 
		for (int i = 0; i < years.size(); i++)
			if (years.get(i) == "1892")
				isHistoricEvent = true;
		//if it finds it, verifies it's there.  If it's not, adds 1892 to the list
		if (isHistoricEvent)
			System.out.println("Founding of CMU was a historic event.");
		else {
			System.out.println("Founding of CMU will be added to the list.");
			years.add("1892");
		}
		
		//sorts the event into the list
		Collections.sort(years);
		//prints the fixed list
		System.out.println(years);
		
		inFile.close();
	}

}
