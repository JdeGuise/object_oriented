import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

//InvalidTriangleException.java, John deGuise, Homework 2, 9/16/15		
//Custom exception class for Triangle.java

public class InvalidTriangleException extends Exception{
	
	
	public InvalidTriangleException(){
		super();
	}
	
	public InvalidTriangleException(String message){
		super(message);
		
	}

}
