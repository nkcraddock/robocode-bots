using System;
using System.Collections.Generic;
using System.Text;

namespace NKC.RobotsOfDeath.Maths
{
    public static class Mathinator
    {
        public static double BulletSpeed(double firePower)
        {
            return 20 - 3 * firePower;
        }

        public static double NormalizeBearing(double angle)
        {
            while (angle > 180) angle -= 360;
            while (angle < -180) angle += 360;
            return angle;
        }

        public static double MaximumEscapeAngle(double firePower)
        {
            return Math.Asin(8.0 / (20 - (3 * Math.Min(3, firePower))));
        }

        public static Point Project(Point source, double angle, double length)
        {
            double x = source.x + Math.Sin(angle) * length;
            double y = source.y + Math.Cos(angle) * length;

            return new Point(x, y);
        }

        public static double AbsoluteBearing(Point source, Point target)
        {
            return Math.Atan2(target.y - source.y, target.x - source.x);
        }
    }
}
