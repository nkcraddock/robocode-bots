package nkc;

import java.awt.geom.Point2D;

class VomitTools {
	static double bulletSpeed(double firePower) { 
		return 20 - 3 * firePower; 
	}
	
	static double normalizeBearing(double angle) {
		while (angle >  180) angle -= 360;
		while (angle < -180) angle += 360;
		return angle;
	}	
	
	static double maxEscapeAngle(double firePower) {
		return Math.asin(8.0 / (20 - (3 * Math.min(3,firePower))));
	}
	
	static Point2D project(Point2D sourceLocation, double angle, double length) {
		return new Point2D.Double(sourceLocation.getX() + Math.sin(angle) * length,
				sourceLocation.getY() + Math.cos(angle) * length);
	}
	
	static double absoluteBearing(Point2D source, Point2D target) {
		return Math.atan2(target.getX() - source.getX(), target.getY() - source.getY());
	}
	
}