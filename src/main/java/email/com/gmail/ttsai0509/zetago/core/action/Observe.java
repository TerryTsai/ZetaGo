package email.com.gmail.ttsai0509.zetago.core.action;

import com.google.inject.Inject;
import email.com.gmail.ttsai0509.zetago.core.common.Grid;
import email.com.gmail.ttsai0509.zetago.core.component.Position;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Observe {

    @Inject Grid grid;

    private final Executor executor = Executors.newFixedThreadPool(1);
    private final int timeout = 100;

    public void submit(final Position position) {
        executor.execute(() -> {



        });
    }

}
