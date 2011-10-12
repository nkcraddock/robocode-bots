package nkc;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class VomitComet extends Gun {
	
	public VomitComet(AdvancedRobot r) {
		super(r);
	}

	public void onScannedRobot(ScannedRobotEvent e, Bot target) {
		double absoluteBearing = robot.getHeadingRadians() + target.bearingRadians;
		robot.setTurnGunRightRadians(getLeadGunTurnRadians(absoluteBearing, target.velocity, target.headingRadians));
		fireWhenReady(getFirePower(target), e.getDistance());
	}

	double getFirePower(Bot target) {
		return Math.min(400 / target.distance, 3);
	}

	double getLeadGunTurnRadians(double absoluteBearing, double enemeyVelocity, double enemyHeadingRadians) {
		double gunHeading = absoluteBearing - robot.getGunHeadingRadians();
		return Utils.normalRelativeAngle(gunHeading + (enemeyVelocity * Math.sin(enemyHeadingRadians - absoluteBearing) / 13));
	}
}
