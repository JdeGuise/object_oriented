import java.util.concurrent.Semaphore;

public class HappyMom {
	Room room = new Room();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final Semaphore semaphore = new Semaphore(4);
		
		Thread mom = new Thread();
		Thread adam = new Thread();
		Thread bob = new Thread();
		Thread caroline = new Thread();
		Thread darcy = new Thread();
	
	}
	private class Mom implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
	}
	private class Child{
		
		
		
	}
	private class Room {
		public void putCookies(){
		//called by mom thread
			//for cookie plate
			//if cookie plate.contains < 10 cookies
			//	make cookie
			//wake children
			//return amount of cookies made
			
		}	
		public void eatCookies(){
		//called by child thread
			//if room.contains 1 or 2 seats, can enter room
			//else, room is occupied and child can't enter
			
			//if cookies remaining > amount desired, eats them
			//if no, child waits inside room for mom thread to make more cookies
			//child must eat exactly how many they want, all at once (can't eat 4 ready of the 5 wanted)
			//once the child has eaten every cookie they want, they must immediately leave
			
		}
	}
	
}
