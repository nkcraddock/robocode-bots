using Robocode;
using System;
using System.Collections.Generic;
using System.Text;

namespace NKC.RobotsOfDeath.Parts
{
    public abstract class RobotPart
    {
        public virtual void Initialize()
        {
        }

        public virtual void OnScannedRobot(ScannedRobotEvent e)
        {
        }

        public virtual void OnPaint(IGraphics graphics)
        {
        }

        public virtual void OnHitWall(HitWallEvent e)
        {
        }

        public virtual void OnRobotDeath(RobotDeathEvent e)
        {
            
        }

        public virtual void OnHitByBullet(HitByBulletEvent e)
        {
        }
    }
}
