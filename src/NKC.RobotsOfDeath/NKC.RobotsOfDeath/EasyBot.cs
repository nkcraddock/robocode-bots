using NKC.RobotsOfDeath.Parts;
using Robocode;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Text;

namespace NKC.RobotsOfDeath
{
    public class EasyBot : RobotBase
    {
        public EasyBot()
        {

            robotParts = new RobotPart[] {
                new BasicTargeting(this),
                new TargetLockRadar(this),
                new BasicGun(this),
                new BasicStrafeSteering(this) { TrailingDistance = 200, MaxDive = 65 }
            };
        }
    }

}
