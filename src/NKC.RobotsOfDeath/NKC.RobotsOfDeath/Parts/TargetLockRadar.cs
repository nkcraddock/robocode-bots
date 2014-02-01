
using Robocode;
using Robocode.Util;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Text;
using NKC.RobotsOfDeath.Maths;

namespace NKC.RobotsOfDeath.Parts
{
    public class TargetLockRadar : RobotPart
    {
        RobotBase robot;

        public TargetLockRadar(RobotBase robot)
        {
            this.robot = robot;
        }

        public override void OnScannedRobot(ScannedRobotEvent e)
        {
            if (robot.Target == null || robot.Target.name != e.Name)
                return;

            double radarTurn = robot.HeadingRadians + e.BearingRadians - robot.RadarHeadingRadians;
            robot.SetTurnRadarRightRadians(Utils.NormalRelativeAngle(radarTurn));
        }

        public override void Initialize()
        {
            robot.RadarColor = Color.Yellow;
        }
    }

}
