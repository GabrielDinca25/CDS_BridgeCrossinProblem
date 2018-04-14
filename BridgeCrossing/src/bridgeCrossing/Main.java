package bridgeCrossing;
import java.util.Scanner;
public class Main {

	public static void main(String[] args) {
			
			int bridgeCapacity, firstDirection, numberOfLeftVehicles, numberOfRightVehicles;
			Scanner scanner = new Scanner(System.in);
			
			System.out.println("Enter the bridge's maximum capacity");
			bridgeCapacity = scanner.nextInt();
			System.out.println("Enter the starting direction of the cars ( 0 = left side of the bridge, 1 = right side of the bridge");
			firstDirection = scanner.nextInt();
			System.out.println("Enter the number of the cars on the left side of the bridge");
			numberOfLeftVehicles = scanner.nextInt();
			System.out.println("Enter the number of the cars on the right side of the bridge");
			numberOfRightVehicles = scanner.nextInt();
			
			Bridge bridge = new Bridge(bridgeCapacity, firstDirection, numberOfLeftVehicles, numberOfRightVehicles);
			scanner.close();
			bridge.run();
			
		
		
	}

}
