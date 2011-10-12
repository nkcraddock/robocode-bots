package nkc;

import robocode.ScannedRobotEvent;

public class Bot {
	public double bearingRadians;
	public double headingRadians;
	public double distance;
	public String name;
	public double energy;
	public double heading;
	public double velocity;
	
	static Bot fromScannedRobotEvent(ScannedRobotEvent e) {
		Bot bot = new Bot();
		
		bot.energy = e.getEnergy();
		bot.velocity = e.getVelocity();
		bot.heading = e.getHeading();
		bot.bearingRadians = e.getBearingRadians();
		bot.distance = e.getDistance();
		bot.name = e.getName();
		bot.headingRadians = e.getHeadingRadians();
		
		return bot;
	}
}
