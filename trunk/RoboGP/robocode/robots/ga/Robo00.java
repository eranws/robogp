package ga;
import robocode.*;
import java.awt.Color;
public class Robo00 extends Robot{
private static final boolean GUN_ROBOT = false;
 private static final boolean RADAR_ROBOT = false;
public void run(){
setAdjustGunForRobotTurn(GUN_ROBOT);
setAdjustRadarForRobotTurn(RADAR_ROBOT);
while(true){
turnRadarLeft(146.25);ahead(37.5);turnRadarRight(90.0);turnLeft(78.75);
}
}
public void onScannedRobot(ScannedRobotEvent e) {
ahead(37.5);turnRadarRight(90.0);turnLeft(78.75);turnLeft(56.25);
}public void onHitByBullet(HitByBulletEvent e) {
turnRadarRight(90.0);turnLeft(78.75);turnLeft(56.25);turnGunRight(101.25);
}public void onBulletHit(BulletHitEvent event){
turnLeft(78.75);turnLeft(56.25);turnGunRight(101.25);turnGunLeft(101.25);
}public void	onHitRobot(HitRobotEvent event){
turnLeft(56.25);turnGunRight(101.25);turnGunLeft(101.25);fire(0.825);
}public void onHitWall(HitWallEvent event){
turnGunRight(101.25);turnGunLeft(101.25);fire(0.825);fire(0.1);
}}