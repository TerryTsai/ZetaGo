package email.com.gmail.ttsai0509.zetago.core.noise;

import email.com.gmail.ttsai0509.zetago.core.common.Math;
import email.com.gmail.ttsai0509.zetago.core.common.Point;

public enum Voronoi {

    ; // Utility

    public static Point nearest(int x, int y, int z, Point... points) {
        float bestDist2 = Float.MAX_VALUE;
        Point bestPoint = null;

        for (Point currPoint : points) {
            float currDist2 = Math.dst2(x, y, z, currPoint.x, currPoint.y, currPoint.z);
            if (currDist2 < bestDist2) {
                bestDist2 = currDist2;
                bestPoint = currPoint;
            }
        }

        return bestPoint;
    }

}
