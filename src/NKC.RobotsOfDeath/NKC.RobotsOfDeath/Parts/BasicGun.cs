using Robocode;
using Robocode.Util;
using System;
using System.Collections.Generic;
using System.Text;

namespace NKC.RobotsOfDeath.Parts
{
    public class BasicGun : RobotPart
    {
        RobotBase robot;
        public double FiringRange { get; set; }

        public BasicGun(RobotBase robot)
        {
            this.robot = robot;
            this.FiringRange = 450;
        }

        public override void OnScannedRobot(ScannedRobotEvent e)
        {
            if (robot.Target == null)
                return;
            var target = robot.Target;

            double absoluteBearing = robot.HeadingRadians + target.bearingRadians;
            robot.SetTurnGunRightRadians(getLeadGunTurnRadians(absoluteBearing, target.velocity, target.headingRadians));
            FireWhenReady(e.Distance);
                
        }

        void FireWhenReady(double distance)
        {
            if (robot.GunHeat == 0 && robot.GunTurnRemaining < 10 && distance < FiringRange)
                robot.Fire(Math.Min(400 / distance, 3));
        }

        double getLeadGunTurnRadians(double absoluteBearing, double enemeyVelocity, double enemyHeadingRadians)
        {
            double gunHeading = absoluteBearing - robot.GunHeadingRadians;
            return Utils.NormalRelativeAngle(gunHeading + (enemeyVelocity * Math.Sin(enemyHeadingRadians - absoluteBearing) / 13));
        }
    }
}
