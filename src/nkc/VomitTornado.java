package nkc;

import java.awt.Color;


import robocode.AdvancedRobot;
import robocode.HitWallEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

public class VomitTornado extends AdvancedRobot {

	ISteering steering;
	Gun gun;
	Bot target;
	
	public void run() {
		steering = new RedSteering(this);
		gun = new RedGun(this);
		//steering = new BlueSteering(this);
		//gun = new VomitComet(this);
		
		setBodyColor(Color.black);
        setAdjustRadarForGunTurn(true);
        setAdjustGunForRobotTurn(true);
        
        while (true) turnRadarLeft(Double.POSITIVE_INFINITY);
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		if(target == null) 
			target = Bot.fromScannedRobotEvent(e, this);
		
		if(target.name != e.getName()) 
			return; // We don't care.
		
		target = Bot.fromScannedRobotEvent(e, this);
		steering.onScannedRobot(e, target);
		gun.onScannedRobot(e, target);
	}
	
	public void onHitWall(HitWallEvent e) {
		steering.onHitWall(e);
	}

	public void onRobotDeath(RobotDeathEvent e) {
		if(target != null && target.name == e.getName())
			target = null; // Our enemy is slain.
	}
	
	public void onWin(WinEvent event) {
		turnLeft(360);
	}
}




