
public class Test{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Truck truck1 = new Truck(123, "Honda", 3.0);
		
		
		Truck truck2 = new Truck(3456, "Ford", 5.1);
		
		if (truck1.compareTo(truck2) > 0){
			System.out.println(truck1 + " \n is larger than \n"+ truck2);
		}
	}

}
