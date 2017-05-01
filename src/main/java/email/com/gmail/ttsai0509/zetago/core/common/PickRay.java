package email.com.gmail.ttsai0509.zetago.core.common;

import java.lang.*;

@SuppressWarnings({"Duplicates", "RedundantIfStatement", "StatementWithEmptyBody", "UnusedAssignment"})
public enum PickRay {

    ; // Utility

    private static final float EPSILON = 0.0001f;

    /**
     * Find the first instance of a value between 2 points in a 3d grid.
     *
     * @param val value to search
     * @param ax  starting x
     * @param ay  starting y
     * @param az  starting z
     * @param bx  ending x
     * @param by  ending y
     * @param bz  ending z
     * @param w   width of map
     * @param h   height of map
     * @param d   depth of map
     * @param map flattened map data with order width, height, depth
     * @return (x, y, z) coordinates of first instance of the value in the ray or null if not found
     */
    public static Point gridPick(int val,
                                  float ax, float ay, float az,
                                  float bx, float by, float bz,
                                  int w, int h, int d, int[] map
    ) {
        float t = 0;
        float x = ax, y = ay, z = az;
        float mx = bx - ax, my = by - ay, mz = bz - az;

        do {
            int ix = Math.floor(x), iy = Math.floor(y), iz = Math.floor(z);

            if (ix >= 0 && iy >= 0 && iz >= 0
                    && ix < w && iy < h && iz < d
                    && map[ix + (w * iy) + (w * h * iz)] == val) {
                return new Point(ix, iy, iz);
            }

            float t1x = (mx > 0) ? (float) Math.ceil(x) : (float) Math.floor(x) - EPSILON;
            float t2y = (my > 0) ? (float) Math.ceil(y) : (float) Math.floor(y) - EPSILON;
            float t3z = (mz > 0) ? (float) Math.ceil(z) : (float) Math.floor(z) - EPSILON;

            float t1t = (t1x - ax) / mx;
            float t2t = (t2y - ay) / my;
            float t3t = (t3z - az) / mz;

            if (t1t >= 0 && (t2t < 0 || t1t <= t2t) && (t3t < 0 || t1t <= t3t)) {
                t = t1t;
                x = t1x;
                y = ay + (my * t1t);
                z = az + (mz * t1t);
            } else if (t2t >= 0 && (t1t < 0 || t2t <= t1t) && (t3t < 0 || t2t <= t3t)) {
                t = t2t;
                x = ax + (mx * t2t);
                y = t2y;
                z = az + (mz * t2t);
            } else {
                t = t3t;
                x = ax + (mx * t3t);
                y = ay + (my * t3t);
                z = t3z;
            }

        } while (t < 1);

        return null;
    }

    public static boolean lineInRegion(
            float p1x, float p1y, float p1z,
            float p2x, float p2y, float p2z,
            float c1x, float c1y, float c1z,
            float c2x, float c2y, float c2z
    ) {

        if (p1x < c1x && p2x < c1x) return false;
        if (p1x > c2x && p2x > c2x) return false;
        if (p1y < c1y && p2y < c1y) return false;
        if (p1y > c2y && p2y > c2y) return false;
        if (p1z < c1z && p2z < c1z) return false;
        if (p1z > c2z && p2z > c2z) return false;

        return true;
    }

}
