//Triangle.java, John deGuise, Homework 2, 9/16/15		
//


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Triangle {
	
	public double a, b, c;
	public boolean isNotTriangle = true;
	
	public static void main(String[] args)  throws InvalidTriangleException, IOException{
		
		File f = new File("HW2.txt");						//Create new output file with IOException
		Scanner input = new Scanner(System.in);
		
		Triangle triangle1 = new Triangle(1.7, 2.7, 3);
		
		double perimeter = getPerimeter(triangle1);
		
		System.out.println("The perimeter is " + perimeter + ".\n");
		
		input.close();
	}
	

	public Triangle(double a, double b, double c) {
		try{
			if((a + b) <= c || (a + c) <= b || (b + c) <= a){ 
				
				throw new InvalidTriangleException("The sum of any two sides is less than or equal to the other side.\n");
			}
		}
			
		catch (InvalidTriangleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
								
	}
	
	public static double getPerimeter(Triangle t){			//perimeter of Triangle edges represented as double
		System.out.println("Perimeter = " + (t.a + t.b + t.c));
		
		return t.a + t.b + t.c;
	}
	
	public static String toString(String t){				//string representation of Triangle edge lengths
		System.out.println(t);
		return t;
	
	}

}
