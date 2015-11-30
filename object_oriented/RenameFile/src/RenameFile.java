import java.io.File;
import java.io.IOException;

public class RenameFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("cs015.jar");
		
		File file2 = new File("pacMap.jar");
		
		if (file2.exists()){
			try {
				throw new java.io.IOException("file exists");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		boolean success = file.renameTo(file2);
			
		if(!success){
				
		}
		
		try {
			java.io.FileWriter out = new java.io.FileWriter(file2, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
