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
		setTurnRadarRight(e.getBearing());
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
			g.fillOval((int)them.getX()-5, (int)them.getY()-5, 10, 10);
			g.drawLine((int)me.getX(), (int)me.getY(), (int)them.getX(), (int)them.getY());
			double bearing = VomitTools.normalizeBearing((int)(target.absoluteBearing*180/Math.PI));
			g.drawArc((int)me.getX()-(int)(target.distance), (int)me.getY()-(int)(target.distance), 
					(int)target.distance*2, (int)target.distance*2, 
					(int)bearing-25, 50);
		}
		
		drawAGrid(g);
		
	}
	
	public void onHitWall(HitWallEvent e) {
		steering.onHitWall(e);
	}
	
	void drawAGrid(Graphics2D g) {
		g.setColor(Color.white);
		double h = (int)getBattleFieldHeight();
		double w = (int)getBattleFieldWidth();
		double xdiv = 12.5;
		double ydiv = 9.42;
	
		for(double x = 0; x < w; x+= w / xdiv) {
			g.drawLine((int)x, 0, (int)x, (int)h - 25);
			g.drawString(Integer.toString((int)x), (int)x - 10, (int)h - 20);
		}
		
		for(double y = h - h / ydiv; y > 0; y -= h / ydiv) {
			g.drawLine(40, (int)y, (int)w, (int)y);
			g.drawString(Integer.toString((int)y), 5, (int)y - 5);
		}
	}
	
	Point2D getLocation() {
		return new Point2D.Double(getX(), getY());
	}
	
}
