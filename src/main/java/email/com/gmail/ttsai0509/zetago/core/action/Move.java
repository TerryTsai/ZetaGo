package email.com.gmail.ttsai0509.zetago.core.action;

import com.google.inject.Inject;
import email.com.gmail.ttsai0509.zetago.core.common.*;
import email.com.gmail.ttsai0509.zetago.core.component.Position;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Move {

    @Inject private Grid grid;
    @Inject private PathFinder pathFinder;
    @Inject private PathFinder2 pathFinder2;

    private final Executor executor = Executors.newFixedThreadPool(5);
    private final int timeout = 100;

    public void submit(final Position position, final int x, final int y, final int z) {
        executor.execute(() -> {

            int count = 0;

            List<Point> path = pathFinder2.solve(
                    position.getX(), position.getY(), position.getZ(), x, y, z,
                    grid.width, grid.height, grid.depth, grid.getRaw());

            Iterator<Point> itr = path.iterator();

            while (!position.at(x, y, z) && itr.hasNext() && (count++ < timeout)) {

                Point next  = itr.next();

                position.set(next.x, next.y, next.z);

                Threads.sleepQuietly(100);

            }

        });
    }

}
