package ga;
import robocode.*;
import java.awt.Color;
public class Robo02 extends Robot{
private static final boolean GUN_ROBOT = false;
 private static final boolean RADAR_ROBOT = false;
public void run(){
setAdjustGunForRobotTurn(GUN_ROBOT);
setAdjustRadarForRobotTurn(RADAR_ROBOT);
while(true){
fire(1.1875);turnRadarLeft(135.0);turnGunLeft(67.5);turnRight(45.0);
}
}
public void onScannedRobot(ScannedRobotEvent e) {
fire(1.1875);turnRadarLeft(135.0);turnGunLeft(67.5);turnRight(45.0);
}public void onHitByBullet(HitByBulletEvent e) {
fire(1.1875);turnRadarLeft(135.0);turnGunLeft(67.5);turnRight(45.0);
}public void onBulletHit(BulletHitEvent event){
fire(1.1875);turnRadarLeft(135.0);turnGunLeft(67.5);turnRight(45.0);
}public void	onHitRobot(HitRobotEvent event){
fire(1.1875);turnRadarLeft(135.0);turnGunLeft(67.5);turnRight(45.0);
}public void onHitWall(HitWallEvent event){
fire(1.1875);turnRadarLeft(135.0);turnGunLeft(67.5);turnRight(45.0);
}}