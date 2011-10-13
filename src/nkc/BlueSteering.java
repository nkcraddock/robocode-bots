package nkc;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Double;
import java.util.Random;

import robocode.AdvancedRobot;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

public class BlueSteering implements ISteering {
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
	Rectangle2D fieldRect = new Rectangle2D.Double(18, 18, battleFieldWidth-36, battleFieldHeight-36);
	
	public BlueSteering(AdvancedRobot r) {
		robot = r;
		robot.setRadarColor(Color.blue);
	}
	
	public void onScannedRobot(ScannedRobotEvent e, Bot target) {
		if(target != null && lastTarget != null && target.energy < lastTarget.energy && r.nextDouble() < dodge) // son of a bitch shot at me!
			reverseDirection();
		
		robot.setTurnRadarRight(VomitTools.normalizeBearing(robot.getHeading() - robot.getRadarHeading() + e.getBearing()));
		Point2D location = new Point2D.Double(robot.getX(), robot.getY());
		Point2D enemyLocation = VomitTools.project(location, e.getBearing(), e.getDistance());
		//double absBearing = VomitTools.absoluteBearing(location, enemyLocation);
		
		//robot.setTurnRight(absBearing);
			goTo(new Point2D.Double(100,100));
		

		lastTarget = target;
	}
	
	void goTo(Point2D point) {
		Point2D location = new Point2D.Double(robot.getX(), robot.getY());
		double absoluteBearing = VomitTools.absoluteBearing(location, point) - robot.getHeadingRadians();
		//System.out.println(absoluteBearing);
		//System.out.println("(" + location.getX() + "," + location.getY() + ") - (" + point.getX() + "," + point.getY() + ") = " + absoluteBearing + " (" + turn + ")");
		robot.setTurnRight(VomitTools.normalizeBearing(robot.getHeading() - absoluteBearing));
		robot.setAhead(Math.cos(absoluteBearing) * 100);
		//System.out.println(robot.getX() + "," + robot.getY());
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
