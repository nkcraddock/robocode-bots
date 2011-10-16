package nkc;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class Draw {
	// Draws an arc with x,y as the center instead of the bottom left like retard mode 
	public static void arc(Graphics2D g, double x, double y, double r, double a, double length) {
		g.drawArc((int)(x - r), (int)(y - r), (int)r*2, (int)r*2, (int)a, (int)length);
	}
	
	public static void grid(Graphics2D g, double h, double w) {
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
	
	public static void fillCircle(Graphics2D g, Point2D p, double d) {
		fillCircle(g, p.getX(), p.getY(), d);
	}
	
	public static void fillCircle(Graphics2D g, double x, double y, double d) {
		g.fillOval((int)Math.round(x - (d / 2)), 
				(int)Math.round(y - (d / 2)), 
				(int)Math.round(d), 
				(int)Math.round(d));
	}
	
	public static void line(Graphics2D g, Point2D p1, Point2D p2) {
		g.drawLine((int)Math.round(p1.getX()), (int)Math.round(p1.getX()), (int)Math.round(p2.getY()), (int)Math.round(p2.getY()));
	}
}
