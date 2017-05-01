package email.com.gmail.ttsai0509.zetago.core.common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@SuppressWarnings("Duplicates")
public class PathFinder2 {

    private final int nodesW, nodesH, nodesD;
    private final Heuristic comparator;

    public PathFinder2(int nodesW, int nodesH, int nodesD) {
        this.nodesW = nodesW;
        this.nodesH = nodesH;
        this.nodesD = nodesD;
        this.comparator = new Heuristic();
    }

    /**
     * Standard A* pathfinder
     *
     * @param srcX  starting x
     * @param srcY  starting y
     * @param srcZ  starting z
     * @param destX ending x
     * @param destY ending y
     * @param destZ ending z
     * @param mapW  width of map
     * @param mapH  height of map
     * @param mapD  depth of map
     * @param map   flattened map data with order width, height, depth
     * @return list of points indicating shortest path from start to end
     */
    public List<Point> solve(
            int srcX, int srcY, int srcZ,
            int destX, int destY, int destZ,
            int mapW, int mapH, int mapD, int[] map
    ) {

        // Validate Arguments
        if (map.length != mapW * mapH * mapD)
            throw new IllegalArgumentException("Map dimensions do not match data");
        if (nodesW < mapW || nodesH < mapH || nodesD < mapD)
            throw new IllegalArgumentException("Map dimensions too large");
        if (srcX < 0 || srcX >= mapW || destX < 0 || destX >= mapW)
            throw new IllegalArgumentException("Point out of bounds");
        if (srcY < 0 || srcY >= mapH || destY < 0 || destY >= mapH)
            throw new IllegalArgumentException("Point out of bounds");
        if (srcZ < 0 || srcZ >= mapD || destZ < 0 || destZ >= mapD)
            throw new IllegalArgumentException("Point out of bounds");

        // Reset Temp Fields
        Node[] nodes = new Node[nodesW * nodesH * nodesD];
        for (int i = 0; i < nodes.length; i++)
            nodes[i] = new Node(i % nodesW, i / nodesW % nodesH, i / nodesW / nodesH % nodesD);
        PriorityQueue<Node> queue = new PriorityQueue<>(comparator);

        comparator.setDestination(srcX, srcY, srcZ);
        for (Node node : nodes)
            node.reset();

        // Prepare Start Node
        Node start = nodes[getIndex(destX, destY, destZ, nodesW, nodesH, nodesD)];
        start.updateBest(0, null);
        queue.add(start);

        // Execute PathFinding
        State state = State.SEARCH;
        while (!queue.isEmpty() && state == State.SEARCH) {
            Node curr = queue.poll();
            curr.close();

            if (curr.getDistanceTo(srcX, srcY, srcZ) == 0) {
                state = State.FOUND;

            } else for (Direction dir : Direction.values()) {
                int nextX = curr.x + dir.x;
                if (nextX < 0 || nextX >= nodesW)
                    continue;

                int nextY = curr.y + dir.y;
                if (nextY < 0 || nextY >= nodesH)
                    continue;

                int nextZ = curr.z + dir.z;
                if (nextZ < 0 || nextZ >= nodesD)
                    continue;

                Node next = nodes[getIndex(nextX, nextY, nextZ, nodesW, nodesH, nodesD)];
                if (next.isClosed())
                    continue;

                int nextCurrKnownCost = curr.getBestKnownCost() + map[getIndex(nextX, nextY, nextZ, mapW, mapH, mapD)];
                if (nextCurrKnownCost < next.getBestKnownCost()) {
                    next.updateBest(nextCurrKnownCost, curr);
                    queue.remove(next);
                    queue.add(next);
                }
            }
        }

        // Construct Path
        if (state == State.FOUND) {
            List<Point> path = new ArrayList<>();

            Node curr = nodes[getIndex(srcX, srcY, srcZ, nodesW, nodesH, nodesD)];

            while (curr.getDistanceTo(destX, destY, destZ) != 0) {
                path.add(new Point(curr.x, curr.y, curr.z));
                curr = curr.getPrev();
            }

            path.add(new Point(curr.x, curr.y, curr.z));
            return path;

        } else {
            return null;
        }
    }

    private enum State {
        SEARCH, FOUND
    }

    private enum Direction {

        F       ( 0, 0, 1),
        B       ( 0, 0,-1),
        L       (-1, 0, 0),
        R       ( 1, 0, 0),
        FL      (-1, 0, 1),
        FR      ( 1, 0, 1),
        BL      (-1, 0,-1),
        BR      ( 1, 0,-1),

        UF      ( 0, 1, 1),
        UB      ( 0, 1,-1),
        UL      (-1, 1, 0),
        UR      ( 1, 1, 0),
        UFL     (-1, 1, 1),
        UFR     ( 1, 1, 1),
        UBL     (-1, 1,-1),
        UBR     ( 1, 1,-1),

        DF      ( 0,-1, 1),
        DB      ( 0,-1,-1),
        DL      (-1,-1, 0),
        DR      ( 1,-1, 0),
        DFL     (-1,-1, 1),
        DFR     ( 1,-1, 1),
        DBL     (-1,-1,-1),
        DBR     ( 1,-1,-1),
        ;

        final int x, y, z;

        Direction(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public static int distance(int fromX, int fromY, int fromZ, int toX, int toY, int toZ) {
            int dx = java.lang.Math.abs(toX - fromX);
            int dy = java.lang.Math.abs(toY - fromY);
            int dz = java.lang.Math.abs(toZ - fromZ);
            return java.lang.Math.max(java.lang.Math.max(dx, dy), dz);
        }

    }

    private static class Node {

        final int x, y, z;
        private int bestKnownCost;
        private Node bestKnownPrev;
        private boolean closed;

        Node(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        int getDistanceTo(int toX, int toY, int toZ) {
            return Direction.distance(x, y, z, toX, toY, toZ);
        }

        int getBestKnownCost() {
            return bestKnownCost;
        }

        boolean isClosed() {
            return this.closed;
        }

        Node getPrev() {
            return this.bestKnownPrev;
        }

        void updateBest(int bestKnownCost, Node bestKnownPrev) {
            this.bestKnownCost = bestKnownCost;
            this.bestKnownPrev = bestKnownPrev;
        }

        void close() {
            this.closed = true;
        }

        void reset() {
            bestKnownCost = Integer.MAX_VALUE;
            bestKnownPrev = null;
            closed = false;
        }

    }

    private static class Heuristic implements Comparator<Node> {

        private int x, y, z;

        void setDestination(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public int compare(Node o1, Node o2) {
            return (o1.getBestKnownCost() + o1.getDistanceTo(x, y, z)) -
                    (o2.getBestKnownCost() + o2.getDistanceTo(x, y, z));

        }

    }

    private static int getIndex(int x, int y, int z, int w, int h, int d) {
        return x + (y * w) + (z * w * h);
    }

}
