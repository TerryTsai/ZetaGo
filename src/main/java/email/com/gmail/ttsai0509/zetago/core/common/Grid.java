package email.com.gmail.ttsai0509.zetago.core.common;

import email.com.gmail.ttsai0509.zetago.core.noise.Simplex;
import email.com.gmail.ttsai0509.zetago.core.noise.Voronoi;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("Duplicates")
public class Grid {

    public final int width, height, depth;
    private final int[] data;

    public Grid(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.data = new int[width * depth * height];
    }

    public int get(int x, int y, int z) {
        return data[x + (y * width) + (z * width * height)];
    }

    public void set(int x, int y, int z, int val) {
        data[x + (y * width) + (z * width * height)] = val;
    }

    public int[] getRaw() {
        return data;
    }

    public static class Builder {

        private final int width, height, depth;
        private float[] map;

        public Builder(int width, int height, int depth) {
            this.width = width;
            this.height = height;
            this.depth = depth;
            this.map = new float[width * depth];
        }

        public Builder simplex(
                float sclW, float sclH, float sclD,
                float offW, float offH, float offD) {
            for (int x = 0; x < width; x++) {
                for (int z = 0; z < depth; z++) {
                    float dx = (float) x / sclW + offW;
                    float dz = (float) z / sclD + offD;
                    float dy = ((float) Simplex.noise(dx, dz) + 1.0f) / 2.0f * sclH + offH;
                    map[x + (z * width)] += dy;
                }
            }
            return this;
        }

        public Builder voronoi(int count) {

            Point[] points = new Point[count];
            Map<Point, Integer> pointMap = new HashMap<>();
            for (int i = 0; i < count; i++) {
                Point point = new Point(
                        Math.random.nextInt(width),
                        Math.random.nextInt(height),
                        Math.random.nextInt(depth));
                points[i] = point;
                pointMap.put(point, i);
            }

            for (int x = 0; x < width; x++) {
                for (int z = 0; z < depth; z++) {
                    Point point = Voronoi.nearest(x, 0, z, points);
                    map[x + (z * width)] = pointMap.get(point);
                }
            }
            return this;
        }

        public Builder peaks(int height, int radius, int count) {
            for (int c = 0; c < count; c++) {
                int x = Math.random.nextInt(width);
                int z = Math.random.nextInt(depth);
                map[x + (z * width)] += height;
            }
            return this;
        }

        public Grid build() {
            Grid grid = new Grid(width, height, depth);
            for (int x = 0; x < width; x++) {
                for (int z = 0; z < depth; z++) {
                    int yy = (int) map[x + (z * width)];
                    for (int y = 0; y < height; y++) {
                        if (y < yy) {
                            grid.set(x, y, z, 1000);
                        } else if (y == yy) {
                            grid.set(x, y, z, 999);
                        } else if (y == yy + 1) {
                            grid.set(x, y, z, 1);
                        } else {
                            grid.set(x, y, z, 1000);
                        }
                    }
                }
            }
            this.map = null;
            return grid;
        }

    }
}
