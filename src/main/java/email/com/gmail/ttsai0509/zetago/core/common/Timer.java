package email.com.gmail.ttsai0509.zetago.core.common;

public enum Timer {

    ; // Utility Class

    private static final long MILLISECOND = 1000000L;
    private static final long SECOND = 1000000000L;

    public static void time(String name, Runnable r) {
        long tic = System.nanoTime();
        r.run();
        long toc = System.nanoTime();

        long nanosecond = toc - tic;
        long millisecond = nanosecond / MILLISECOND;
        long second = nanosecond / SECOND;
        System.out.println(String.format("%15.15s : %10dns %10dms %10ds", name, nanosecond, millisecond, second));
    }

}
