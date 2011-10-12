package nkc;

import java.awt.Color;
import java.util.Random;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;


public class ShittyGun implements IGun{
	
	Random r = new Random();
	double firingRange = 500;
	AdvancedRobot robot;

	public ShittyGun(AdvancedRobot r) {
		robot = r;
		robot.setGunColor(Color.white);
	}

	public void onScannedRobot(ScannedRobotEvent e, Bot target) {
		fireWhenReady(target);
	}

	void fireWhenReady(Bot target) {
		double firePower = Math.min(400 / target.distance, 3);
		double absoluteBearing = robot.getHeadingRadians() + target.bearingRadians;
		robot.setTurnGunRightRadians(getLeadGunTurnRadians(absoluteBearing, target.velocity, target.headingRadians));
		if (robot.getGunHeat() == 0 && robot.getGunTurnRemaining() < 10 && inRange(target.distance))
			robot.fire(firePower);
	}
	
	boolean inRange(double distance) {
		return distance <= firingRange;
	}

	double getLeadGunTurnRadians(double absoluteBearing, double enemeyVelocity, double enemyHeadingRadians) {
		double lol = 13.0 + (3 *  r.nextDouble()); 
		double gunHeading = absoluteBearing - robot.getGunHeadingRadians();
		return Utils.normalRelativeAngle(gunHeading + (enemeyVelocity * Math.sin(enemyHeadingRadians - absoluteBearing) / lol));
	}
}
