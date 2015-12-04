import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class HappyMom {
	Room room = new Room();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Mom mom = new Mom();
		mom.run();
		Child adam = new Child("Adam");
		Child bob = new Child("Bob");
		Child caroline = new Child("Caroline");
		Child darcy = new Child("Darcy");
		
		
		adam.run();
		bob.run();
		caroline.run();
		darcy.run();
		

			
	}
}

class Mom implements Runnable{
	int kitchenStrikes = 0;
	@Override
	public void run() {
		System.out.println("Mom is running.");
		// TODO Auto-generated method stub
		while(isNotDone()){			
			Room.putCookies(); //put the cookies in the Room
			
			try {
				System.out.println("Mom is sleeping.");
				Thread.sleep(1000); //mom sleeps for 1 second
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Mom killed herself."); 
				e.printStackTrace();
			}
			System.out.println("Mom made cookies.  Reseting kitchen strikes.");
			kitchenStrikes = 0; //reset the consecutive counter
		}

	}
	
	public boolean isNotDone(){
		boolean notDone = true;
		if(Room.cookiePlate == 10){
			notDone = false;
		}
		
		if(Room.putCookies() == 0){
			kitchenStrikes++;
			System.out.println("We baked no cookies (cookie plate full).");
		}
		if(kitchenStrikes == 1){
			System.out.println("Kids didn't want cookies once. Strike 1");
		}
		if(kitchenStrikes == 2){
			System.out.println("Kids didn't want cookies two times in a row.");
		}
		if(kitchenStrikes == 3){
			notDone = false;
			System.out.println("Kids didn't want cookies three times in a row. Mom's having wine.");
		}
		
		return notDone;
	}
	
}
class Child implements Runnable{
	int snack;
	String name;
	
	public Child(String name){
		this.name = name;
		snack = 0;
	}
	
	@Override
	public void run() {
		System.out.println(name + " running!");

		int cookiesEaten = 0;
		// TODO Auto-generated method stub
		while(cookiesEaten < 20){
			snack = Room.eatCookies((int)(Math.random()*10+1));  //eat between 1 and 10 cookies
			System.out.println(snack + " is snack size");
			cookiesEaten += snack;
		}
		System.out.println(name + " is done eating cookies.  Total eaten: " + cookiesEaten);

		
		try {
			System.out.println(name + " playing outside.");
			Thread.sleep(1000); //kid plays outside for 1 second
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(name + " killed their self while playing. Noob"); 
			e.printStackTrace();
		}
	}
	
	
//	checkRoom(); //child first checks room
	
	
}
class Room {
	static int cookiePlate;
	public Room() {
		final Semaphore semaphore = new Semaphore(2);
		cookiePlate = 10;
	}
	public static int putCookies(){
		int cookiesMade = 0;
		System.out.println("Cookie Plate currently has " + cookiePlate + ".");
		if(cookiePlate != 10){
			System.out.println("Making cookies.");
			while(cookiePlate < 10){
				cookiePlate++;
				cookiesMade++;
			}
			System.out.println("Made " + cookiesMade + " cookies.");
		}
		
		//wake children up
		
		
		return cookiesMade;
	}	
	
	public static int eatCookies(int snackSize){
	//called by child thread
		
		//if room.contains 1 or 2 seats, can enter room
		//else, room is occupied and child can't enter
		
		//if cookies remaining > amount desired, eats them
		//if no, child waits inside room for mom thread to make more cookies
		//child must eat exactly how many they want, all at once (can't eat 4 ready of the 5 wanted)
		//once the child has eaten every cookie they want, they must immediately leave
	
		return snackSize;
	}
}
