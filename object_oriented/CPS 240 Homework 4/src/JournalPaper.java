import java.util.List;

//JournalPaper.java, John deGuise, Homework 4, 10/9/15

//is-a publication, has journal name, starting and ending page#
//implement constructor(s), which call superclass getPageNumbers-> returns total pages of journal
//don't forget toString()

public class JournalPaper extends Publication{
	
	//Journal-specific constants
	private String JNAME;
	private int STARTPAGE;
	private int ENDPAGE;
	
	public JournalPaper(String title, int year, String publisher, String journalName, int startpage, int endpage, List<String> authors){
		
		super(title, year, publisher, authors); //super to parent Publication
		
		STARTPAGE = startpage;
		ENDPAGE = endpage;
		JNAME = journalName;
		
	}
	
	public int getPageNumbers(){
		return ENDPAGE - STARTPAGE;
	}
	
	public String toString(){
		return "Journal: " + TITLE + "; Year: " + YEAR + "; Publisher: " + PUBLISHER + "; Journal Name: " + JNAME + "; Start Page: " + STARTPAGE + "; End Page: " + ENDPAGE + "; Total Pages: " + getPageNumbers() + "; Authors: " + getAuthors();
	}

}
