import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class AliceCounter {

	public static void main(String[] args) throws IOException{
		File inputFile = new File("Alice.txt");
		Scanner inFile = new Scanner(inputFile);
		String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
		int[] letterCounts = new int[26];
		String currentLine;
		
		while(inFile.hasNextLine()){
			currentLine = inFile.nextLine().toUpperCase();
			for (int i = 0; i < currentLine.length(); i++){
				char currentLetter = currentLine.charAt(i);
				switch(currentLetter){
				case 'A': letterCounts[0]++; break;
				case 'B': letterCounts[1]++; break;
				case 'C': letterCounts[2]++; break;
				case 'D': letterCounts[3]++; break;
				case 'E': letterCounts[4]++; break;
				case 'F': letterCounts[5]++; break;
				case 'G': letterCounts[6]++; break;
				case 'H': letterCounts[7]++; break;
				case 'I': letterCounts[8]++; break;
				case 'J': letterCounts[9]++; break;
				case 'K': letterCounts[10]++; break;
				case 'L': letterCounts[11]++; break;
				case 'M': letterCounts[12]++; break;
				case 'N': letterCounts[13]++; break;
				case 'O': letterCounts[14]++; break;
				case 'P': letterCounts[15]++; break;
				case 'Q': letterCounts[16]++; break;
				case 'R': letterCounts[17]++; break;
				case 'S': letterCounts[18]++; break;
				case 'T': letterCounts[19]++; break;
				case 'U': letterCounts[20]++; break;
				case 'V': letterCounts[21]++; break;
				case 'W': letterCounts[22]++; break;
				case 'X': letterCounts[23]++; break;
				case 'Y': letterCounts[24]++; break;
				case 'Z': letterCounts[25]++; break;
				}
			}
		}
			for (int i = 0; i < letterCounts.length; i++){
				System.out.println(letters[i] + ": " + letterCounts[i]);
			}
			inFile.close();
	}

}
