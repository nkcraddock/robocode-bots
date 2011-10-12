package nkc;

import java.awt.Color;
import java.util.Random;

import robocode.AdvancedRobot;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

public class VomitSteering implements ISteering {
	Random r = new Random();
	AdvancedRobot robot;
	double direction = -1;
	int directionGettingOld;
	double battleFieldWidth = 800;
	double battleFieldHeight = 600;
	double speed = 50;
	double attackOrRetreat = -1;
	double trailingDistance = 150;
	Bot lastTarget;
	double dodge = 0.2;
	
	public VomitSteering(AdvancedRobot r) {
		robot = r;
		robot.setRadarColor(Color.red);
	}
	
	public void onScannedRobot(ScannedRobotEvent e, Bot target) {
		if(target != null && lastTarget != null && target.energy < lastTarget.energy && r.nextDouble() < dodge) // son of a bitch shot at me!
			reverseDirection();
		
		robot.setTurnRadarRight(VomitTools.normalizeBearing(robot.getHeading() - robot.getRadarHeading() + e.getBearing()));
		robot.setTurnRight(VomitTools.normalizeBearing(e.getBearing() + 90 - angleOfAttack(target)));
		robot.setAhead(speed * direction);
		
		lastTarget = target;
	}
	void reverseDirection() {
		direction *= -1;
		directionGettingOld = 0;
	}
	
	double angleOfAttack(Bot e) {
		double attackOrRetreat = (e.distance < trailingDistance) ? -1 : 1;
		double width = robot.getBattleFieldWidth();
		double factor = ((e.distance - trailingDistance) / width) * 5;
		double maxDive = 60;
		return Math.abs(factor * maxDive) * direction * attackOrRetreat;
	}

	public void onHitWall(HitWallEvent event) {
		directionGettingOld = -100;
		reverseDirection();
	}
}
