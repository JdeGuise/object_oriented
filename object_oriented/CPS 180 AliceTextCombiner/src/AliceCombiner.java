import java.util.*;
import java.io.*;
public final class AliceCombiner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}	
	
	public static void PieceWriter() throws IOException {
		File inputFile;
		PrintWriter outFile = new PrintWriter("Alice.txt");
		Scanner input;
		String[] files = {"wonder1", "wonder2", "wonder3", "wonder4"};
	/* iterates through wonder1-4 and appends .txt, takes the contents of each, 
	writes to outFile(which is Alice.txt) while there are wonder#.txts to iterate through. */
		for(int i=0;i<4;i++){
			inputFile = new File(files[i] + ".txt");
			input = new Scanner(inputFile);
			while(input.hasNext()) {
				outFile.write(input.nextLine() + "\n");
			}
			input.close();
		}
		outFile.close();
	}

}
