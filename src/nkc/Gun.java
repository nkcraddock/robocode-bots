package nkc;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public abstract class Gun {
	
	AdvancedRobot robot;
	public Gun(AdvancedRobot r) {
		robot = r;
	}
	public double firingRange = 500;
	
	public abstract void onScannedRobot(ScannedRobotEvent e, Bot target);
	
	void fireWhenReady(double power, double distance) {
		if (robot.getGunHeat() == 0 && robot.getGunTurnRemaining() < 10 && inRange(distance)) {
			robot.fire(power);
		}
	}
	
	boolean inRange(double distance) {
		return distance <= firingRange;
	}
	
}
