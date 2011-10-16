package nkc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import robocode.AdvancedRobot;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

public class SohCahToa extends AdvancedRobot {
	Bot target;
	ArrayList<Point2D> points = new ArrayList<Point2D>();
	ISteering steering;
	
	public void run() {
		steering = new RedSteering(this);
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		
		while(true) turnRadarLeft(Double.POSITIVE_INFINITY);
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		target = Bot.fromScannedRobotEvent(e, this);
		steering.onScannedRobot(e, target);
	}
	public void onPaint(Graphics2D g) {
		Point2D me = getLocation();
		
		g.setColor(Color.green);
		g.fillOval((int)me.getX(), (int)me.getY(), 10, 10);
		
		if(target != null) {
			Point2D them = target.getLocation();
			g.setColor(Color.red);
			g.fillOval((int)them.getX(), (int)them.getY(), 10, 10);
		}
		
	}
	
	public void onHitWall(HitWallEvent e) {
		steering.onHitWall(e);
	}
	
	
	Point2D getLocation() {
		return new Point2D.Double(getX(), getY());
	}
	
}
