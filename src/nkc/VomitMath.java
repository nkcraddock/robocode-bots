package nkc;

class VomitMath {
	static double bulletSpeed(double firePower) { 
		return 20 - 3 * firePower; 
	}
	
	static double normalizeBearing(double angle) {
		while (angle >  180) angle -= 360;
		while (angle < -180) angle += 360;
		return angle;
	}	
	
	static double maxEscapeAngle(double firePower) {
		return Math.asin(8.0/(20 - (3 *Math.min(3,firePower))));
	}
}