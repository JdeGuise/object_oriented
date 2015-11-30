import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

//Main.java, John deGuise, Homework 4, 10/9/15

public class Main {
	//global static for initDatabases/processQueries
	
	static Map<String, List<Publication>> database;

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		database = new HashMap<String, List<Publication>>();
		initDatabase();
		
		File file = new File("queries.txt");
		processQueries(file);
	}
	
	
	//initialize database HashMap<String, List<Publication>>
	//with values of publicationData.txt
	//in form String = author, List<Publication> = a book or journalpaper object
	public static void initDatabase(){
		
		String journal = "journal";
		String book = "book";
		
		try{
			
			Scanner sc = new Scanner(new File("publicationData.txt"));
			
			sc.useDelimiter("[,\n\r]");
			List<String> authors;
			
			while(sc.hasNextLine()){ //while we have another line and the type we're on isn't empty
				String typeVar = sc.next();
				
				
				if(typeVar.equals(journal)){ //start of journal logic
					authors = new ArrayList<String>();
					boolean nextAuthor = true; //initialize boolean for organizing number of authors
					
					//initializing attributes of journal
					String title = sc.next();
					int year = sc.nextInt();
					String publisher = sc.next();
					String jname = sc.next();
					int startpage = sc.nextInt();
					int endpage = sc.nextInt();
					
					typeVar = sc.next(); //move on to first author

					while(nextAuthor){ //while we have an author

						if(!(typeVar).isEmpty() && typeVar != null && !(authors.contains(typeVar))){ //if our author isn't empty or null
							
							authors.add(typeVar);
						
						}

						
						if(sc.hasNext()){ //if there's another author
							
							typeVar = sc.next(); //set it in a variable			
							
							if(typeVar.equals(book) || typeVar.equals(journal) || typeVar.isEmpty()) { //the scanner has gone around to the next line
								nextAuthor = false;
							}
						
						}
							
						else{
							nextAuthor = false;
						}					
					
					
					
						for(int j = 0; j < authors.size(); j++){
							String auth1 = authors.get(j);
							List<Publication> list = new ArrayList<Publication>();

							if(!(database.containsKey(auth1))){ //doesn't exist, adding a new entry
								
								list.add(new JournalPaper(title, year, publisher, jname, startpage, endpage, authors));
								database.put(auth1,  list);
																
							}
							
							else{ //already exists
								
								list = database.get(auth1);

								list.add(new JournalPaper(title, year, publisher, jname, startpage, endpage, authors));
								database.put(auth1, list);
								
							}
						}
					}
				}
	
				else if(typeVar.equals(book)){
					authors = new ArrayList<String>();
					boolean nextAuthor = true; //initialize boolean for organizing number of authors
					
					//linking attributes to variables
					String title = sc.next();
					int year = sc.nextInt();
					String publisher = sc.next();
					String isbn = sc.next();
					double price = sc.nextDouble();
					int pagenum = sc.nextInt();
										
					typeVar = sc.next();
										
					while(nextAuthor){
						
						if(!(typeVar.equals(" ")) || typeVar != null){
							authors.add(typeVar);
						}
						
						if(sc.hasNext()){
							typeVar = sc.next();
							
							if(typeVar.equals(journal) || typeVar.equals(book) || typeVar.isEmpty()){
								
								nextAuthor = false;
							}
						
						}
						else{
							nextAuthor = false;
						}
					
						List<Publication> list;
						
						for(int i = 0; i < authors.size(); i++){
							String auth1 = authors.get(i);
							
							if(!database.containsKey(auth1)){ //person already exists, adding a publication
								
								list = new ArrayList<Publication>();
								
								list.add(new Book(title, year, publisher, isbn, price, pagenum, authors));
							}
							
							else{ //person does not exist, adding new person entry
								
								list = database.get(auth1);
								
								list.add(new Book(title, year, publisher, isbn, price, pagenum, authors));
								database.put(auth1, list);
							}
						}
						
					}
				}
			}
	
			sc.close();

		}
		
		catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
		}

	}
	
	
	//reads queries.txt into scanner, searches info in db created by initDatabase\
	//returns that it cant find the type of pub., any type of pub., 
	//or returns all pub. answers that fit name and type
	public static void processQueries(File testQueries){
		
		try {
			
			Scanner sc = new Scanner(testQueries);			
			sc.useDelimiter("[,\n]");
			
			while(sc.hasNextLine()){ //while queries.txt still has a line with author and type
				String author = sc.next();
				String type = sc.next();
				
				if(type.equals("book") || type.equals("book\r")){ //fixing case1 formatting issues
					type = "book";
				}
				
				if(type.equals("journal") || type.equals("journal\r")){ //fixing case2 formatting issues
					type = "journal";
				}
				
				//alert for search start
				System.out.println("Searching \"" + author + "\" for \"" + type + "\" .....");
				
				if (database.containsKey(author)) { //if we find the author's name
					List<Publication> list = (ArrayList<Publication>) database.get(author);					
					
					int workCounter = 0; //initialize the amount of works found thusfar
					
					
					for(int i = 0; i < list.size(); i++){
						
						if(type.equals("book") && list.get(i) instanceof Book){ //if the first word is book and our value is instof book
							
							System.out.println(list.get(i));
							workCounter++;
				
						}
						
						else if(type.equals("journal") && list.get(i) instanceof JournalPaper){//if journal and value is instof JP
							
							System.out.println(list.get(i));
							workCounter++;
							
						}
						
					}
					
					
					//result messages
					if (workCounter >= 1){
						
						System.out.println("Author: " + author + " has " + workCounter + " " + type + " publications.\n");
					
					}
					
					//Couldn't find the publication type requested
					else{
						
						System.out.println("No publications of type " + type + " found for author " + author + ".\n");
					
					}
					
				}
				
				
				//couldn't find anything for the author requested
				else{
					
					System.out.println("No publications for author " + author + " found in the database.\n");
					continue;
					
				}
				
			}
			
			sc.close();
		
		} 
		
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}

}
