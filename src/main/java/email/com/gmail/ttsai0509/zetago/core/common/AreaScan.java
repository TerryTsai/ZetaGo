package email.com.gmail.ttsai0509.zetago.core.common;

import java.util.ArrayList;
import java.util.List;

public enum AreaScan {

    ; // Utility

    public interface Test {
        boolean run(int val);
    }

    public static List<Point> scan(Test test,
           int ax, int ay, int az,
           int bx, int by, int bz,
           int mapW, int mapH, int mapD, int[] map
    ) {

        // Validate Arguments
        if (map.length != mapW * mapH * mapD)
            throw new IllegalArgumentException("Map dimensions do not match data");
        if (ax < 0 || ax >= mapW || bx < 0 || bx >= mapW)
            throw new IllegalArgumentException("Point out of bounds");
        if (ay < 0 || ay >= mapH || by < 0 || by >= mapH)
            throw new IllegalArgumentException("Point out of bounds");
        if (az < 0 || az >= mapD || bz < 0 || bz >= mapD)
            throw new IllegalArgumentException("Point out of bounds");

        int px = ax > bx ? bx : ax;
        int py = ay > by ? by : ay;
        int pz = az > bz ? bz : az;

        int qx = ax > bx ? ax : bx;
        int qy = ay > by ? ay : by;
        int qz = az > bz ? az : bz;

        List<Point> points = new ArrayList<>();
        for (int i = px; i < qx; i++) {
            for (int j = py; j < qy; j++) {
                for (int k = pz; k < qz; k++) {
                    if (test.run(map[i + (j * mapW) + (k * mapW * mapH)])) {
                        points.add(new Point(i, j, k));
                    }
                }
            }
        }

        return points;
    }

}
