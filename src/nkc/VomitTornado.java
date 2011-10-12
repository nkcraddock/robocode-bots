package nkc;

import java.awt.Color;
import java.util.Random;

import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;
import robocode.util.Utils;

public class VomitTornado extends AdvancedRobot {
	
	Bot target;
	double direction = -1;
	int directionGettingOld;
	double attackOrRetreat = -1;
	double trailingDistance = 150;
	double attentionSpan = 70;
	double speed = 50;
	double margin = 50;
	Random r;
	double dodge = 0.2;
	
	public void run() {
		r = new Random();
		setColors(Color.green, Color.white, Color.red);
        setAdjustRadarForGunTurn(true);
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForRobotTurn(true);

        while (true) {
        	turnRadarLeft(20 * direction);
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		if(target == null)
			target = Bot.fromScannedRobotEvent(e);
		
		if(target.name != e.getName())
			return; // We don't care.
		
		Bot oldEnemy = target;
		target = Bot.fromScannedRobotEvent(e);
		
		if(target.energy < oldEnemy.energy && r.nextDouble() < dodge) // son of a bitch shot at me!
			reverseDirection();
		
		fireWhenReady(target);
		
		
		setTurnRadarRight(VomitMath.normalizeBearing(getHeading() - getRadarHeading() + e.getBearing()));
		setTurnRight(VomitMath.normalizeBearing(e.getBearing() + 90 - angleOfAttack(target)));
		setAhead(speed * direction);
		
	}
	
	public void onHitWall(HitWallEvent event) {
		directionGettingOld = -100;
		reverseDirection();
	}
	
	public void onHitByBullet(HitByBulletEvent event) {
		
	}
	
	public void onWin(WinEvent event) {
		turnLeft(360);
	}
	
	void reverseDirection() {
		direction *= -1;
		directionGettingOld = 0;
	}
	
	double angleOfAttack(Bot e) {
		double attackOrRetreat = (e.distance < trailingDistance) ? -1 : 1;
		double width = getBattleFieldWidth();
		double factor = ((e.distance - trailingDistance) / width) * 5;
		double maxDive = 60;
		return Math.abs(factor * maxDive) * direction * attackOrRetreat;
	}
	
	void fireWhenReady(Bot e) {
		double firePower = Math.min(400 / e.distance, 3);
		double absoluteBearing = getHeadingRadians() + e.bearingRadians;
		setTurnGunRightRadians(getLeadGunTurnRadians(absoluteBearing, e.velocity, e.headingRadians));
		if (getGunHeat() == 0 && getGunTurnRemaining() < 10 && inRange(e.distance))
			fire(firePower);
	}
	
	boolean inRange(double distance) {
		return distance <= trailingDistance * 2.0;
	}

	double getLeadGunTurnRadians(double absoluteBearing, double enemeyVelocity, double enemyHeadingRadians) {
		double whatsThisNumber = 13.0 + (3 *  r.nextDouble()); 
		double gunHeading = absoluteBearing - getGunHeadingRadians();
		return Utils.normalRelativeAngle(gunHeading + (enemeyVelocity * Math.sin(enemyHeadingRadians - absoluteBearing) / whatsThisNumber));
	}
	
}




