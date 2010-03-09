package ga;
import robocode.*;
//import java.awt.Color;

/**
 * RoboTemp - a robot by (your name here)
 */
public class RoboTemp extends Robot
{
	/**
	 * run: RoboTemp's default behavior
	 */
	public void run() {
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			ahead(100);
			turnGunRight(360);
			back(100);
			turnGunRight(360);
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		fire(1);
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		turnLeft(90 - e.getBearing());
	}
	
}
