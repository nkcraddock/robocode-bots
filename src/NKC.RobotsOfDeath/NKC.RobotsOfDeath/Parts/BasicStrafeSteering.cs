using NKC.RobotsOfDeath.Maths;
using Robocode;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Text;

namespace NKC.RobotsOfDeath.Parts
{
    public class BasicStrafeSteering : RobotPart
    {
        protected RobotBase robot;
        Random r = new Random();
        double direction = -1;
        double lastEnergy = 0;
        double directionAge = 0;
        public double MaxDive { get; set; }

        public double DodgeFactor { get; set; }
        public double Speed { get; set; }
        public double TrailingDistance { get; set; }
        public double DirectionAgeIncrement { get; set; }


        public BasicStrafeSteering(RobotBase robot)
        {
            this.robot = robot;
            DodgeFactor = 0.5;
            Speed = 50;
            TrailingDistance = 150;
            DirectionAgeIncrement = 0.0001;
            MaxDive = 60;
        }

        public override void OnScannedRobot(ScannedRobotEvent e)
        {
            const double PADDING = 100;
            if (directionAge > 0 && 
                (robot.X < PADDING || robot.X > robot.BattleFieldWidth - PADDING || robot.Y < PADDING || robot.Y > robot.BattleFieldHeight - PADDING))
                OnHitWall(new HitWallEvent(0));

            var target = robot.Target;
            if (target == null)
                return;
            bool shotsFired = (target != null && lastEnergy > 0 && target.energy < lastEnergy);

            if (directionAge > 0 && ((shotsFired && r.NextDouble() < DodgeFactor) || r.NextDouble() < directionAge))
                reverseDirection();

            robot.SetTurnRadarRight(Mathinator.NormalizeBearing(robot.Heading - robot.RadarHeading + e.Bearing));
            robot.SetTurnRight(Mathinator.NormalizeBearing(e.Bearing + 90 - angleOfAttack(target)));
            robot.SetAhead(Speed * direction);
            directionAge += DirectionAgeIncrement;

            if(target != null)
                lastEnergy = target.energy;
        }

        public override void OnHitWall(HitWallEvent e)
        {
            reverseDirection();
            directionAge = DirectionAgeIncrement * -15;
        }

        public override void OnHitByBullet(HitByBulletEvent e)
        {
            TrailingDistance = r.Next(100, 250);
        }

        void reverseDirection()
        {
            direction *= -1;
            directionAge = 0;
        }

        double angleOfAttack(ScannedBot target)
        {
            double attackOrRetreat = (target.distance < TrailingDistance) ? -1 : 1;
            double width = robot.BattleFieldWidth;
            double factor = ((target.distance - TrailingDistance) / width) * 5;
            return Math.Abs(factor * MaxDive) * direction * attackOrRetreat;
        }
    }
}
