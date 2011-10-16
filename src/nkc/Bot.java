package nkc;

import java.awt.geom.Point2D;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class Bot {
	public double bearingRadians;
	public double headingRadians;
	public double absoluteBearing;
	public double distance;
	public String name;
	public double energy;
	public double heading;
	public double velocity;
	public double x;
	public double y;
	
	
	static Bot fromScannedRobotEvent(ScannedRobotEvent e, AdvancedRobot scanner) {
		Bot bot = new Bot();
		bot.energy = e.getEnergy();
		bot.velocity = e.getVelocity();
		bot.heading = e.getHeading();
		bot.bearingRadians = e.getBearingRadians();
		bot.distance = e.getDistance();
		bot.name = e.getName();
		bot.headingRadians = e.getHeadingRadians();
		bot.absoluteBearing = scanner.getHeadingRadians() + e.getBearingRadians();
		
		bot.x = scanner.getX() + e.getDistance() * Math.sin(bot.absoluteBearing);
		bot.y = scanner.getY() + e.getDistance() * Math.cos(bot.absoluteBearing);
		
		return bot;
	}
	
	Point2D getLocation() {
		return new Point2D.Double(x, y);
	}
}
