import java.util.ArrayList;
import java.util.List;

//Publication.java John deGuise, Homework 4, 10/9/15

//Needs to be: abstract, is-a published document
//implement getTitle, getYear, getAuthors, getPublisher
//all classes inheriting from Publication must have getPageNumbers methods -10pts

public abstract class Publication implements Document{
	
	//Publication-specific constants
	String TITLE;
	int YEAR;
	List<String> AUTHORS = new ArrayList<String>();
	String PUBLISHER;
	
	public Publication(String title, int year, String publisher, List<String> authors){ //constructor to be referenced by Book/Journal super()
		
		TITLE = title;
		YEAR = year;
		PUBLISHER = publisher;
		AUTHORS = authors;
	
	}

	public String getTitle(){
		return TITLE;
	}
	
	public int getYear(){
		return YEAR;
	}
	
	public String getPublisher(){
		return PUBLISHER;
	}
	
	public List<String> getAuthors(){
		return AUTHORS;
	}
	


}
