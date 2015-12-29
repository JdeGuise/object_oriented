//John deGuise, HappyMom.java, CPS 240 (Qi Liao), 12/9/15 5PM

import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class HappyMom 
{
	
	//statics to keep track of our ChildList, and the amount of total cookies
	private static Room room = new Room();

	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		Child adam = new Child("Adam");
		Child bob = new Child("Bob");
		Child caroline = new Child("Caroline");
		Child darcy = new Child("Darcy");
		Mom mom = new Mom();
		childList.add(adam);
		childList.add(bob);
		childList.add(caroline);
		childList.add(darcy);

		mom.run();
	
		System.out.println("Child (Adam):   ate " + adam.cookiesEaten + " cookies.");
		System.out.println("Child (Bob):   ate " + bob.cookiesEaten + " cookies.");
		System.out.println("Child (Caroline):   ate " + caroline.cookiesEaten + " cookies.");
		System.out.println("Child (Darcy):   ate " + darcy.cookiesEaten + " cookies.");
		System.out.println("Mom: made " + totalCookiesMade + " cookies.");
		System.out.println(Room.cookiePlate + " cookies left on the plate.");

	}

}

class Mom implements Runnable{
	@Override
	public void run() {
		System.out.println("Mom:    enters the room. " + Room.diningRoom.availablePermits() + " open seats, " + Room.cookiePlate + " cookies on the cookie plate.");
		
		//puts cookies if cookieplate != 10 or we didn't already have a putCookies() return 0 in the prev. cycle
		while(continueCooking()){			
			Room.putCookies(); //put the cookies in the Room
			
			try {
				System.out.println("Mom:   tired, sleeping 1 second.");
				Thread.sleep(1000); //mom sleeps for 1 second
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Mom? Mom?!?!?!?!?"); 
				e.printStackTrace();
			}
		}

	}
	
	public boolean continueCooking(){

		//boolean check method for whether we baked cookies the last cycle, and if our plate is full
		boolean notDone = true;
		if(Room.cookiePlate == 10){
			notDone = false;
		}
		
		if(Room.putCookies() == 0){
			System.out.println("We baked no cookies (cookie plate full).");
			notDone = false;
		}
		
		return notDone;
	}
	
}
class Child implements Runnable{
	static int snack;
	String name;
    int cookiesEaten = 0;
    
	public Child(String childName){
		cookiesEaten = 0;
		this.name = childName;
		snack = -1;
	}
	
	public String getName(Child child){
		
		return child.name;
		
	}
	
	@Override
	public void run() {
		
		//snack = random int of value 1-10
	    snack = ((int)(Math.random()*10+1));  //eat between 1 and 10 cookies
	    
	    //if kid has had less than 20 cookies, and there's an available permit
		if(!isDone() && Room.diningRoom.availablePermits() > 0){
			System.out.println("Child (" + name + "): enters the room. " + Room.diningRoom.availablePermits() + " open seats, and " + Room.cookiePlate + " cookies on the plate.");
			System.out.println("Child (" + name + "): wants " + snack + " cookies.  We have " + Room.cookiePlate);
			
			//if desired snacksize is bigger than the plate, we have to make more cookies.
			if(snack > Room.cookiePlate){
				System.out.println("CookiePlate:   Not enough cookies.  Mom must make more.");
				Room.putCookies();
			}
			//else, we're good to go, so we eat, add to our running counter
			else if(snack <= Room.cookiePlate){	
				Room.eatCookies(snack);
				cookiesEaten += snack;
				System.out.println(name + ":   done eating cookies.  Total eaten:   " + cookiesEaten);
			}
		}
		
		//else if the kid has had enough cookies
		else if(isDone()){
			System.out.println(name + ":   has had at least 20 cookies, and now diabetes.");
		}
		//else if there's not enough permits to get inside
		else if(Room.diningRoom.availablePermits() == 0){
			System.out.println(name + ":   Not enough permits.  Stopped at the door.");
		}
		
		//kid is outside / sleeping
		try {
			System.out.println(name + " playing outside.");
			Thread.sleep(1000); //kid plays outside for 1 second
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(name + " has errored out.  This got morbid."); 
			e.printStackTrace();
		}
	}
	
	public boolean isDone(){
		
		if (cookiesEaten >= 20){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	//x wants cookies, there aren't enough, mom makes more- this method is a check for if
	//x still needs to stay in the room
	public boolean isFed(){

		if(snack == cookiesEaten && cookiesEaten < 20){
			return true;
		}

		return false;
	}	
	
}
class Room {
	static int cookiePlate;
	final static Semaphore diningRoom = new Semaphore(2);
	final static Lock cookieLock = new ReentrantLock();
		
	public Room() {
		cookiePlate = 0;
	}

	
	//put cookies if the plate is < 10, and add to our cumulative baking count
	public static int putCookies(){

		int cookiesMade = 0;
		if(cookiePlate != 10){
			cookieLock.lock();
			while(cookiePlate < 10){
				cookiePlate++;
				cookiesMade++;
			}
			cookieLock.unlock();
			System.out.println("Mom:   Made " + cookiesMade + " cookies.");
			HappyMom.totalCookiesMade += cookiesMade;
		}
		
		//wake children up
		for(Child nextChild : HappyMom.childList){
			
			//if there's a permit, child acquires and enters
			if(diningRoom.availablePermits() > 0){
				try {
					System.out.println("Child (" + nextChild.getName(nextChild) + "):   wants to enter the dining room.");

					diningRoom.acquire();
					System.out.println("Child (" + nextChild.getName(nextChild) + "):   Acquired permit. " + diningRoom.availablePermits() + " still available.");

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//run child to eat cookies
				nextChild.run();
				//once the child has eaten every cookie they want, they must immediately leave
				diningRoom.release();
				System.out.println("Child (" + nextChild.getName(nextChild) + "):   Released semaphore permit. " + diningRoom.availablePermits() + " available.");
			}
			else{
				//not enough permits
				System.out.println("No room at the inn.  Skipping " + nextChild.getName(nextChild) + ".");
			}
		}
		
		
		return cookiesMade;
	}	
	
	public static int eatCookies(int snack){
	//called by child thread

		if(snack <= cookiePlate){
			
			//we have enough cookies to sate the child
			System.out.println("Eating " + snack + " cookies.");
			cookiePlate -= snack;
			System.out.println(cookiePlate + " cookies remaining.");
			
		}
		else{
			//we don't have enough cookies
			System.out.println("Not enough cookies available.  Cannot eat until mom makes more!!");
		}
		if(diningRoom.availablePermits() == 0){
			//else, room occupied and child can't enter
			System.out.println("No more seats at the table. \n");
		}

	
		return snack;
	}
}
