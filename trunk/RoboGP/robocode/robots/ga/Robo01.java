package ga;
import robocode.*;
import java.awt.Color;
public class Robo01 extends Robot{
private static final boolean GUN_ROBOT = false;
 private static final boolean RADAR_ROBOT = false;
public void run(){
setAdjustGunForRobotTurn(GUN_ROBOT);
setAdjustRadarForRobotTurn(RADAR_ROBOT);
while(true){
turnLeft(168.75);turnRadarRight(112.5);fire(1.1875);turnRight(0.0);
}
}
public void onScannedRobot(ScannedRobotEvent e) {
turnLeft(168.75);turnRadarRight(112.5);fire(1.1875);turnRight(0.0);
}public void onHitByBullet(HitByBulletEvent e) {
turnLeft(168.75);turnRadarRight(112.5);fire(1.1875);turnRight(0.0);
}public void onBulletHit(BulletHitEvent event){
turnLeft(168.75);turnRadarRight(112.5);fire(1.1875);turnRight(0.0);
}public void	onHitRobot(HitRobotEvent event){
turnLeft(168.75);turnRadarRight(112.5);fire(1.1875);turnRight(0.0);
}public void onHitWall(HitWallEvent event){
turnLeft(168.75);turnRadarRight(112.5);fire(1.1875);turnRight(0.0);
}}