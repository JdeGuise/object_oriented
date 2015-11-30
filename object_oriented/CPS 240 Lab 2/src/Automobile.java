
public abstract class Automobile implements Computer{
	protected int mpg;
	private String make;
	
	public Automobile(String make){
		
		this.make = make;
		
	}
	
	public String toString(){
				
		return "Automobile: "+make;
	
	}

	public abstract double getMPG();
	
}
