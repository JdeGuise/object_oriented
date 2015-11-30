import java.util.List;

//Book.java, John deGuise, Homework 4, 10/9/15

//is-a publication, 
//methods to implement: constructor(s) -> superclass const. getPageNumbers - returns total pages,
//toString, returns all information about a book -10pts

public class Book extends Publication{
	
	//Book-specific constants
	private int PAGENUM;
	private String ISBN;
	private double PRICE;
	
	
	public Book(String title, int year, String publisher, String isbn, double price, int pagenum, List<String> authors){
		super(title, year, publisher, authors); //super to parent Publication
		
		ISBN = isbn;
		PRICE = price;
		PAGENUM = pagenum;
	}

	
	public double getPrice(){
		return PRICE;
	}
	
	public String getISBN(){
		return ISBN;
	}
	
	public String toString(){
		return "Book: " + TITLE + "; Year: " + YEAR + "; Publisher " + PUBLISHER + "; ISBN: " + ISBN + "; Price: " + PRICE + "; No. of pages: " + PAGENUM + "; Authors: " + getAuthors();
	}
}
