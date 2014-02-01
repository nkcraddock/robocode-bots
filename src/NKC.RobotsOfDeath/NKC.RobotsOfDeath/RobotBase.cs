using NKC.RobotsOfDeath.Parts;
using Robocode;
using System;
using System.Collections.Generic;
using System.Text;

namespace NKC.RobotsOfDeath
{
    public abstract class RobotBase : AdvancedRobot
    {
        public ScannedBot Target { get; set; }

        protected RobotPart[] robotParts;


        public override void Run()
        {
            IsAdjustGunForRobotTurn = true; 
            IsAdjustRadarForGunTurn = true;
            IsAdjustRadarForRobotTurn = true;

            foreach (var part in robotParts)
                part.Initialize();

            while (true)
            {
                TurnRadarRight(380);
                TurnRadarLeft(380);
            }
        }

        public override void OnScannedRobot(ScannedRobotEvent e)
        {
            if (Target != null && Target.name != e.Name)
                return; // Not the target;

            Array.ForEach(robotParts, x => x.OnScannedRobot(e));
        }

        public override void OnPaint(IGraphics graphics)
        {
            Array.ForEach(robotParts, x => x.OnPaint(graphics));
        }

        public override void OnHitWall(HitWallEvent e)
        {
            Array.ForEach(robotParts, x => x.OnHitWall(e));
        }

        public override void OnRobotDeath(RobotDeathEvent e)
        {
            Array.ForEach(robotParts, x => x.OnRobotDeath(e));
        }

        public override void OnHitByBullet(HitByBulletEvent e)
        {
            Array.ForEach(robotParts, x => x.OnHitByBullet(e));
        }
    }
}
