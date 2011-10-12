package nkc;

import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

public interface ISteering {
	void onScannedRobot(ScannedRobotEvent e, Bot target);
	void onHitWall(HitWallEvent e);
}

