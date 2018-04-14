package bridgeCrossing;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bridge {
		
	public static volatile int capacity; //maximum number of cars on the bridge 
	int numberOfLeftVehicles; //number of cars on the left side of the bridge
	int numberOfRightVehicles; //number of cars on the right side of the bridge
	public static volatile int crossingDirection; //0 from the left side of the bridge, 1 from the right side of the bridge, 2 waiting time until the last vehicle crosses the birdge after direction has changed
	int lastCrossingDirection; //variable used for storing the previous crossing direction
	public static volatile boolean bridgeClear ; //boolean for checking if there are vehicles on the bridge
	public static volatile LinkedList<Vehicle> leftVehicles; //linked list for storing the vehicles coming from the left side of the bridge
	public static volatile LinkedList<Vehicle> rightVehicles; //linked list for storing the vehicles coming from the right side of the bridge
	public static volatile LinkedList<Vehicle> bridgeVehicles; //linked list for storing the vehicles that are currently on the bridge
	private Timer timer; //timer thread for periodically changing the crossing direction
	private final Lock _mutex = new ReentrantLock(true); 

	
	Bridge(int capacityNumber, int direction, int numberOfLeftVehicles, int numberOfRightVehicles)
	{
		this.numberOfLeftVehicles = numberOfLeftVehicles;
		this.numberOfRightVehicles = numberOfRightVehicles;
		capacity = capacityNumber; 
		crossingDirection = direction;
		leftVehicles = new LinkedList<>();
		rightVehicles = new LinkedList<>();
		bridgeVehicles = new LinkedList<>();
		bridgeClear = true;
		timer = new Timer();
		timer.start();
		
		for(int i=0;i<this.numberOfLeftVehicles;i++)
		{
			leftVehicles.add(new Vehicle(i, 0, 5 + (int)(Math.random() * 10))); //populating the list of vehicles from the left, with crossing times randomly generated between 5 and 10 seconds
		}
		
		for(int i=0;i<this.numberOfRightVehicles;i++)
		{
			rightVehicles.add(new Vehicle(i, 1,  5 + (int)(Math.random() * 10))); //populating the list of vehicles from the left, with crossing times randomly generated between 5 and 10 seconds
		}
	}
	
	public void run() 
	{	
		while(!leftVehicles.isEmpty() || !rightVehicles.isEmpty())
		{		
			if(crossingDirection == 0) {
				if(bridgeVehicles.size() < capacity) {  
					if(leftVehicles.size() != 0) {
						_mutex.lock();
						bridgeVehicles.add(leftVehicles.poll()); //adding the head of the left sided vehicles list in the list of vehicles on the bridge
						bridgeVehicles.getLast().start();
						_mutex.unlock();
					}
				}
			}else if(crossingDirection == 1) {
				if(bridgeVehicles.size() < capacity) {					
					if(rightVehicles.size() != 0) {
						_mutex.lock();
						bridgeVehicles.add(rightVehicles.poll());//adding the head of the right sided vehicles list in the list of vehicles on the bridge
						bridgeVehicles.getLast().start();
						_mutex.unlock();
					}
				}
			}		
			if(Timer.timer % 10 == 0) { //crossing direction changes every 20 seconds
				ChangeDirection();
			}
		}
		System.out.println(" All cars have crossed the bridge");
	}
	
	//function for changing the crossing direction
	public  void ChangeDirection()
	{
		lastCrossingDirection = crossingDirection;
		crossingDirection = 2;
		while(!bridgeClear) {
			if(bridgeVehicles.size() == 0)
			{
				bridgeClear = true;
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(lastCrossingDirection == 0)
		{
			crossingDirection = 1;
		}
		else if(lastCrossingDirection == 1) 
		{
			crossingDirection = 0;
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}