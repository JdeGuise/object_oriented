//John deGuise, FruitApp.java, Homework 5, 10/16/15

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class FruitApp {
	public static List<Fruit> fruitList;
	static List<String> nutritionLabels;
	public static boolean isValid = false;
	
	public static void main(String[] args) throws FileNotFoundException{
		// TODO Auto-generated method stub
		
		readFruitData();
		selectItem();
				
		MyUtil.selectionSort(fruitList);
		printFruitList();
		
		
	}
	
	//logic for printing our fruitList
	public static void printFruitList(){
		System.out.println(" ");
		
		for(int i = 0; i < fruitList.size(); i++){
			System.out.println(fruitList.get(i));
		}
		
		
		
	}
	
	//read fruit data, and initialize our lists based on how it comes in
	public static void readFruitData() throws FileNotFoundException, InputMismatchException{
		File file = new File("fruit.txt");
		Scanner finput = new Scanner(file);
		finput.useDelimiter("[\t|\n|\r]+");
		
		//initializing object list
		fruitList = new ArrayList<Fruit>();
			
		//no use for "Title" or "Amount", so cycling forward in the scanner
		String titles = finput.next();
		titles = finput.next();
			
		//initializing where our 23 labels will go
		nutritionLabels = new ArrayList<String>();
			
		//adding labels to label list
		for(int i = 0; i < 23; i++){
			nutritionLabels.add(finput.next());
		}
			
			
		//initializing structures to deal with float values
		ArrayList<Float> nutritionValues;
		float[] floatList = new float[100];
			
		//while logic iterating through for each fruit
		while(finput.hasNextLine()){	
			
			//initializing our AL for the 23 float values
			nutritionValues = new ArrayList<Float>();
				
			//grab the name of the fruit and serving amount
			String name = finput.next();
			String amount = finput.next();
				
			//get float value as a string, parse to float, add to our float floatList
			for(int i = 0; i < 23; i++){
				
				String nutrItem = finput.next();
				float nextFloat = Float.parseFloat(nutrItem);
				floatList[i] = nextFloat;
					
			}
				
			//populate our nutritionValues for each fruit and column
			for(int i = 0; i < 23; i++){
				nutritionValues.add(floatList[i]);
			}
				
			//add our final new fruit to fruitList
			fruitList.add(new Fruit(name, amount, nutritionValues, nutritionLabels));

		}	
			
		finput.close();		


	}
	
	//method for printing nutritionLabels
	public static void displayNutrition(){
		int i = 1;
		for(String s: nutritionLabels){
			System.out.println(" " + i + ": " + s);
			i++;
		}
	}
	
	//print our list of nutritional elements to choose from, and prompt for an int for selection
	//while/try logic is for catching exceptions
	public static void selectItem(){
		System.out.println("The following is a list of nutrition items:");
		displayNutrition();
		System.out.println("Please select one nutrition for comparison: ");
		
		
		while(!isValid){
			
			try{
				
				Scanner finput = new Scanner(System.in);
				int userSelection = finput.nextInt();
				
				//writing custom aioobe exception;
				if(userSelection < 1){
					throw new Exception("User selection must be an int greater than 0");
				}
				
				//getting the right row i, and then passing the userSelection int
				for(int i = 0; i < fruitList.size(); i++){
					fruitList.get(i).setChoice(userSelection);
				}
				
				finput.close();
				
				//we made it through without exceptions, so we can leave the while
				isValid = true;
			
			}
			
			catch(InputMismatchException ime){
				ime.printStackTrace();
			} 
			catch (Exception aioobe) {
				aioobe.printStackTrace();
			}
			
		}
	}

}
