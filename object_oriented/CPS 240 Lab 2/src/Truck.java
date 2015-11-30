
public class Truck extends Automobile implements Computer, Comparable<Truck>{
	private double mpg;
	private double version;
	
	public Truck(double mpg, String make, double version) {
		super(make);
		this.mpg = mpg;
		this.version = version;
	}

	public String toString(){
		return super.toString() + ", MPG: "+getMPG()+", version: "+getVersion();
	}

	public double getMPG() {
		// TODO Auto-generated method stub
		return mpg;
	}
	
	public double getVersion(){
		return version;
	}
	
	public int compareTo(Truck t){
		Double m1 = new Double(mpg);
		Double m2 = new Double(t.getMPG());
		return m1.compareTo(m2);
	}
	
	
}
