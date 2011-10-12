package nkc;

import robocode.ScannedRobotEvent;

public interface IGun {
	void onScannedRobot(ScannedRobotEvent e, Bot target);
}
