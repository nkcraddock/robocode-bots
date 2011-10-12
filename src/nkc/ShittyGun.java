package nkc;

import java.awt.Color;
import java.util.Random;


import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;


public class ShittyGun extends Gun {
	
	public ShittyGun(AdvancedRobot r) {
		super(r);
		robot.setGunColor(Color.white);
	}


	Random r = new Random();
	
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
	


	double getLeadGunTurnRadians(double absoluteBearing, double enemeyVelocity, double enemyHeadingRadians) {
		double lol = 13.0 + (3 *  r.nextDouble()); 
		double gunHeading = absoluteBearing - robot.getGunHeadingRadians();
		return Utils.normalRelativeAngle(gunHeading + (enemeyVelocity * Math.sin(enemyHeadingRadians - absoluteBearing) / lol));
	}
}
