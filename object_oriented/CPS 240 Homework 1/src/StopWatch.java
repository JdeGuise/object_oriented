//StopWatch.java, John deGuise, Homework1, 9/9/15		

//Stopwatch implementation that is meant to measure the length of time sustained between processes
//It is not a conventional stopwatch that measures verbosely in milliseconds

public class StopWatch {
	private static double startTime;
	private static double currentTime;

	private StopWatch(){		//default constructor, initialize both startTime and current/endTime to 0
		startTime = 0;		
		currentTime = 0;
	}
	
	public static void start(){
		startTime = System.currentTimeMillis();			//starting so set startTime to the current System time
		
		System.out.println("STOPWATCH STARTED...");
	}
	
	public static void stop(){
		currentTime = System.currentTimeMillis();		//stopping, so set currentTime = to the current System time
		
		System.out.println("STOPWATCH STOPPED.");
		
		if((currentTime - startTime) / 1000 < 1) System.out.println("Full Elapsed Time at Stop: " + (currentTime - startTime) + " millisecond(s)"); //logic to differentiate between times less than 1 second 
		else System.out.println("Full Time Elapsed at Stop: " + ((currentTime - startTime)/ 1000) + " second(s)");									//seconds representation
	}
		
	public static double getStartTime() {
		System.out.println(startTime);
		
		return startTime;
	}
	
	public static double getCurrentTime() {
		return currentTime;
	}

	public static double getElapsedTime(){
		currentTime = System.currentTimeMillis();	
		
		return (currentTime - startTime)/1000;
	}

	public static void main(String[] args) throws InterruptedException {	
		
		StopWatch.start();		//start StopWatch
		
		Thread.sleep(5000);		//for testing, wait 5 seconds
		
		StopWatch.getElapsedTime();		//display elapsed time
		
		Thread.sleep(2000);				//wait two more seconds
		
		StopWatch.getElapsedTime();		//display elapsed time
		
		Thread.sleep(4000);				//wait four more seconds
		
		
		StopWatch.stop();				//stop the watch
	
	}

}
