package bridgeCrossing;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Vehicle extends Thread{
	public int id; //the id of the vehicle
	public int direction; //the direction the vehicle is coming from
	public int crossingTime; //the time it takes for the vehicle to cross the bridge
	
	private boolean bridgeTraveled = false; //boolean for checking if a vehicle has crossed the bridge
	private int timeCrossed; //the time the vehicle will cross the bridge 
	private final Lock _mutex = new ReentrantLock(true);
	
	Vehicle(int id, int direction, int crossingTime)
	{
		this.id = id;
		this.direction = direction;
		this.crossingTime = crossingTime;
	}
		
	public void run()
	{						   
		Bridge.bridgeClear = false;
		timeCrossed = Timer.timer + crossingTime;
		while(!bridgeTraveled) {
			if(Bridge.bridgeVehicles.getFirst() != null  && Bridge.bridgeVehicles.getFirst().id == id && timeCrossed <= Timer.timer) {
				_mutex.lock();
				Bridge.bridgeVehicles.remove();
				_mutex.unlock();
				bridgeTraveled = true;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Vehicle number " + id + " crossed the bridge coming from the " + ((direction==0) ? "left" : "right"));

	}
}
