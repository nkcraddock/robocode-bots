package nkc;

import java.awt.Color;
import java.util.Random;

import robocode.AdvancedRobot;
import robocode.HitWallEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;
import robocode.util.Utils;

public class Beetnik5 extends AdvancedRobot {
	
	ISteering steering;
	Bot target;
	double attentionSpan = 70;
	double margin = 50;
	Random r = new Random();
	double firingRange = 500;
	
	
	public void run() {
		steering = new VomitSteering(this);
		setColors(Color.green, Color.white, Color.red);
        setAdjustRadarForGunTurn(true);
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForRobotTurn(true);

        while (true) {
        	turnRadarLeft(Double.POSITIVE_INFINITY);
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		if(target == null)
			target = Bot.fromScannedRobotEvent(e);
		
		if(target.name != e.getName())
			return; // We don't care.
		
		//Bot oldEnemy = target;
		target = Bot.fromScannedRobotEvent(e);
		
		steering.onScannedRobot(e, target);
		fireWhenReady(target);
	}
	
	public void onHitWall(HitWallEvent e) {
		steering.onHitWall(e);
	}

	public void onRobotDeath(RobotDeathEvent e) {
		if(target != null && target.name == e.getName())
			target = null; // Our enemy is slain.
	}
	
	public void onWin(WinEvent event) {
		turnLeft(360);
	}
	
	
	void fireWhenReady(Bot e) {
		double firePower = Math.min(400 / e.distance, 3);
		double absoluteBearing = getHeadingRadians() + e.bearingRadians;
		setTurnGunRightRadians(getLeadGunTurnRadians(absoluteBearing, e.velocity, e.headingRadians));
		if (getGunHeat() == 0 && getGunTurnRemaining() < 10 && inRange(e.distance))
			fire(firePower);
	}
	
	boolean inRange(double distance) {
		return distance <= firingRange;
	}

	double getLeadGunTurnRadians(double absoluteBearing, double enemeyVelocity, double enemyHeadingRadians) {
		double whatsThisNumber = 13.0 + (3 *  r.nextDouble()); 
		double gunHeading = absoluteBearing - getGunHeadingRadians();
		return Utils.normalRelativeAngle(gunHeading + (enemeyVelocity * Math.sin(enemyHeadingRadians - absoluteBearing) / whatsThisNumber));
	}
}



