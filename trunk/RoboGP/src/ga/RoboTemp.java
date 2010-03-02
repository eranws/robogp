package ga;
import robocode.*;
import java.awt.Color;

public class RoboTemp extends Robot
{
	private static final boolean GUN_ROBOT = false;
	private static final boolean RADAR_ROBOT = false;
	
	public void run() {
		setAdjustGunForRobotTurn(GUN_ROBOT);
		setAdjustRadarForRobotTurn(RADAR_ROBOT);

//		setColors(bodyColor,gunColor,radarColor,bulletColor,scanArcColor); XXX use the same params!

		while(true) {
			/*HERE*/
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		/*HERE*/
		}

	public void onHitByBullet(HitByBulletEvent e) {
		/*HERE*/
	}
	
	public void onBulletHit(BulletHitEvent event){
		/*HERE*/

	}
	public void	onHitRobot(HitRobotEvent event){
		/*HERE*/

	}
	public void onHitWall(HitWallEvent event){
		/*HERE*/
	}

	
}
