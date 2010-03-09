package ga;
import robocode.*;
import java.awt.Color;
public class Robo03 extends Robot{
private static final boolean GUN_ROBOT = false;
 private static final boolean RADAR_ROBOT = false;
public void run(){
setAdjustGunForRobotTurn(GUN_ROBOT);
setAdjustRadarForRobotTurn(RADAR_ROBOT);
while(true){
turnRadarRight(101.25);ahead(0.0);turnLeft(157.5);turnGunLeft(11.25);
}
}
public void onScannedRobot(ScannedRobotEvent e) {
turnRadarRight(101.25);ahead(0.0);turnLeft(157.5);turnGunLeft(11.25);
}public void onHitByBullet(HitByBulletEvent e) {
turnRadarRight(101.25);ahead(0.0);turnLeft(157.5);turnGunLeft(11.25);
}public void onBulletHit(BulletHitEvent event){
turnRadarRight(101.25);ahead(0.0);turnLeft(157.5);turnGunLeft(11.25);
}public void	onHitRobot(HitRobotEvent event){
turnRadarRight(101.25);ahead(0.0);turnLeft(157.5);turnGunLeft(11.25);
}public void onHitWall(HitWallEvent event){
turnRadarRight(101.25);ahead(0.0);turnLeft(157.5);turnGunLeft(11.25);
}}