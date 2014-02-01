using NKC.RobotsOfDeath.Maths;
using Robocode;
using System;
using System.Collections.Generic;
using System.Text;

namespace NKC.RobotsOfDeath.Parts
{
    public class BasicSteering : RobotPart
    {
        AdvancedRobot robot;
        double speed = 50;
        double direction = 1;

        public BasicSteering(AdvancedRobot robot)
        {
            this.robot = robot;
        }

        public override void OnScannedRobot(ScannedRobotEvent e)
        {
            var radarRight = Mathinator.NormalizeBearing(robot.Heading - robot.RadarHeading + e.Bearing);
            var turnRight = Mathinator.NormalizeBearing(e.Bearing);
            var distance = speed * direction;

            robot.SetTurnRadarRight(radarRight);
            robot.SetTurnRight(turnRight);
            robot.SetAhead(distance);
        }
    }
}
