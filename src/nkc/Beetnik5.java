package nkc;

import java.awt.Color;

import robocode.AdvancedRobot;
import robocode.HitWallEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

public class Beetnik5 extends AdvancedRobot {
	
	ISteering steering;
	IGun gun;
	Bot target;
	
	
	
	public void run() {
		steering = new VomitSteering(this);
		gun = new ShittyGun(this);
		
		setBodyColor(Color.green);
        setAdjustRadarForGunTurn(true);
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForRobotTurn(true);

        while (true) {
        	turnRadarLeft(Double.POSITIVE_INFINITY);
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		if(target == null)
			target = Bot.fromScannedRobotEvent(e);
		
		if(target.name != e.getName())
			return; // We don't care.
		
		//Bot oldEnemy = target;
		target = Bot.fromScannedRobotEvent(e);
		
		steering.onScannedRobot(e, target);
		gun.fireWhenReady(target);
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



