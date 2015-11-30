//John deGuise, Fruit.java, Homework 5, 10/16/15

import java.util.ArrayList;
import java.util.List;


public class Fruit implements Comparable<Fruit>{

	String FRUITNAME;
	String AMOUNT;
	List<String> NUTRITIONLABELS = new ArrayList<String>();
	private ArrayList<Float> VALUELIST;
	int userChoice = 1;
	
	//Fruit constructor with name, amount, a list of labels, and a list of the associated values
	public Fruit(String name, String amount, ArrayList<Float> nutritionValues, List<String> nutritionLabels){
		this.FRUITNAME = name;
		this.AMOUNT = amount;
		VALUELIST = nutritionValues;
		NUTRITIONLABELS = nutritionLabels;
	}
	
	//setter for userInput on selectItem
	public void setChoice(int choice){
		userChoice = choice - 1;
	}
	
	public Float getValue(){
		return VALUELIST.get(userChoice);
	}
	
	//cleaned up formatting based on string lengths
	public String toString(){
			if(FRUITNAME.length() < 8){
				if(AMOUNT.length() < 10)
					return FRUITNAME + "\t\t" + AMOUNT + "\t\t\t" + NUTRITIONLABELS.get(userChoice) + " \t" + VALUELIST.get(userChoice); 
				else
					return FRUITNAME + "\t\t" + AMOUNT + "\t\t" + NUTRITIONLABELS.get(userChoice) + " \t" + VALUELIST.get(userChoice); 
			}else{
				if(AMOUNT.length() < 10){
					return FRUITNAME + "\t" + AMOUNT + "\t\t" + NUTRITIONLABELS.get(userChoice) + " \t" + VALUELIST.get(userChoice); 
				}else{
					return FRUITNAME + "\t" + AMOUNT + "\t" + NUTRITIONLABELS.get(userChoice) + " \t" + VALUELIST.get(userChoice); 
				}
			}
	}

	
	//Comparator implementation of compareTo
	@Override
	public int compareTo(Fruit fruit) {
		
		float currentValue = getValue();
		float comparison = fruit.getValue();
		
		if(currentValue > comparison){
			return 1;
		}
		
		else if(currentValue < comparison){
			return -1;
		}
		
		else{
			return 0;
		}
		
	}
	
}
