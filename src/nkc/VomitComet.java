package nkc;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class VomitComet extends Gun {
	
	public VomitComet(AdvancedRobot r) {
		super(r);
	}

	public void onScannedRobot(ScannedRobotEvent e, Bot target) {
		fireWhenReady(getFirePower(target), e.getDistance());
	}

	double getFirePower(Bot target) {
		return Math.min(400 / target.distance, 3);
	}
}
