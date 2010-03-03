package ga;
import robocode.*;
import java.awt.Color;
public class RoboTemp extends Robot{
private static final boolean GUN_ROBOT = false;
 private static final boolean RADAR_ROBOT = false;
public void run(){
setAdjustGunForRobotTurn(GUN_ROBOT);
setAdjustRadarForRobotTurn(RADAR_ROBOT);
while(true){
fire(0.1);turnLeft(67.5);turnRadarLeft(56.25);turnLeft(33.75);
}
}
public void onScannedRobot(ScannedRobotEvent e) {
fire(0.1);turnLeft(67.5);turnRadarLeft(56.25);turnLeft(33.75);
}public void onHitByBullet(HitByBulletEvent e) {
fire(0.1);turnLeft(67.5);turnRadarLeft(56.25);turnLeft(33.75);
}public void onBulletHit(BulletHitEvent event){
fire(0.1);turnLeft(67.5);turnRadarLeft(56.25);turnLeft(33.75);
}public void	onHitRobot(HitRobotEvent event){
fire(0.1);turnLeft(67.5);turnRadarLeft(56.25);turnLeft(33.75);
}public void onHitWall(HitWallEvent event){
fire(0.1);turnLeft(67.5);turnRadarLeft(56.25);turnLeft(33.75);
}}