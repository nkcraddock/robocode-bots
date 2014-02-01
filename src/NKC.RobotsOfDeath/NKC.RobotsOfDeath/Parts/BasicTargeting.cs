using Robocode;
using System;
using System.Collections.Generic;
using System.Text;

namespace NKC.RobotsOfDeath.Parts
{
    public class BasicTargeting : RobotPart
    {
        RobotBase robot;

        public BasicTargeting(RobotBase robot)
        {
            this.robot = robot;
        }

        public override void OnScannedRobot(ScannedRobotEvent e)
        {
            if (robot.Target == null)
                robot.Target = new ScannedBot(e, robot);
            else if(robot.Target.name == e.Name)
                robot.Target.Update(e);
        }

        public override void OnRobotDeath(RobotDeathEvent e)
        {
            if (robot.Target != null && robot.Target.name == e.Name)
                robot.Target = null;
            base.OnRobotDeath(e);
        }
    }
}
