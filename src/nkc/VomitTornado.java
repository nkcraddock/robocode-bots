package nkc;

import java.awt.Color;
import java.util.Random;

import robocode.AdvancedRobot;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;
import robocode.util.Utils;

public class VomitTornado extends AdvancedRobot {
	
	EnemyBot enemy;
	double direction = -1;
	int directionGettingOld;
	double attackOrRetreat = -1;
	double trailingDistance = 150;
	double attentionSpan = 70;
	double bulletDodgery = 0.005;
	double speed = 50;
	double margin = 50;
	Random r;
	double dodge = 0.95;
	
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
	
	private void reverseDirection() {
		direction *= -1;
		directionGettingOld = 0;
	}
	
	private double angleOfAttack(EnemyBot e) {
		double attackOrRetreat = (e.distance < trailingDistance) ? -1 : 1;
		double width = getBattleFieldWidth();
		double factor = (e.distance - trailingDistance) / width;
		double maxDive = 60;
		double angle = factor * 5 * maxDive;
		return Math.abs(angle) * direction * attackOrRetreat;
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		if(enemy == null)
			enemy = EnemyBot.fromScannedRobotEvent(e);
		
		if(enemy.name != e.getName())
			return; // We don't care.
		
		EnemyBot oldEnemy = enemy;
		enemy = EnemyBot.fromScannedRobotEvent(e);
		
		if((directionGettingOld++ > 50 + (100 * r.nextDouble())) || (enemy.energy < oldEnemy.energy && r.nextDouble() < dodge)) // son of a bitch shot at me!
			reverseDirection();
		
		fireWhenReady(enemy);
		
		
		setTurnRadarRight(VomitMath.normalizeBearing(getHeading() - getRadarHeading() + e.getBearing()));
		setTurnRight(VomitMath.normalizeBearing(e.getBearing() + 90 - angleOfAttack(enemy)));
		setAhead(speed * direction);
		
	}
	
	public void onHitWall(HitWallEvent event) {
		directionGettingOld = -100;
		reverseDirection();
	}
	
	public void onWin(WinEvent event) {
		turnLeft(360);
	}
	
	
	
	void fireWhenReady(EnemyBot e) {
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

class EnemyBot {
	public double bearingRadians;
	public double headingRadians;
	public double distance;
	public String name;
	public double energy;
	public double heading;
	public double velocity;
	
	static EnemyBot fromScannedRobotEvent(ScannedRobotEvent e) {
		EnemyBot bot = new EnemyBot();
		
		bot.energy = e.getEnergy();
		bot.velocity = e.getVelocity();
		bot.heading = e.getHeading();
		bot.bearingRadians = e.getBearingRadians();
		bot.distance = e.getDistance();
		bot.name = e.getName();
		bot.headingRadians = e.getHeadingRadians();
		
		return bot;
	}
}

class VomitMath {
	static double bulletSpeed(double firePower) { 
		return 20 - 3 * firePower; 
	}
	
	static double normalizeBearing(double angle) {
		while (angle >  180) angle -= 360;
		while (angle < -180) angle += 360;
		return angle;
	}	
	
	static double maxEscapeAngle(double firePower) {
		return Math.asin(8.0/(20 - (3 *Math.min(3,firePower))));
	}
}


