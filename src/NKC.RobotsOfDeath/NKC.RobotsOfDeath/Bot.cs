using NKC.RobotsOfDeath.Maths;
using Robocode;
using System;
using System.Collections.Generic;
using System.Text;

namespace NKC.RobotsOfDeath
{
    public class ScannedBot
    {
        public double bearingRadians;
        public double headingRadians;
        public double absoluteBearing;
        public double distance;
        public String name;
        public double energy;
        public double heading;
        public double velocity;
        public double x;
        public double y;
        public double bearing;

        AdvancedRobot scanner;


        public ScannedBot(ScannedRobotEvent e, AdvancedRobot scanner)
        {
            this.scanner = scanner;
            Update(e);
        }

        public void Update(ScannedRobotEvent e)
        {
            bearing = e.Bearing;
            energy = e.Energy;
            velocity = e.Velocity;
            heading = e.Heading;
            bearingRadians = e.BearingRadians;
            distance = e.Distance;
            name = e.Name;
            headingRadians = e.HeadingRadians;
            absoluteBearing = scanner.HeadingRadians + e.BearingRadians;

            x = scanner.X + e.Distance * Math.Sin(absoluteBearing);
            y = scanner.Y + e.Distance * Math.Cos(absoluteBearing);
        }

        Point getLocation()
        {
            return new Point(x, y);
        }
    }
}
