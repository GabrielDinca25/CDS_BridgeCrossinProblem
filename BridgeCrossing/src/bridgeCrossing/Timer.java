package bridgeCrossing;



public class Timer extends Thread{	
	public static volatile int timer = 1;
	
	public void run()
	{
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			timer++;
		}
	}
}
