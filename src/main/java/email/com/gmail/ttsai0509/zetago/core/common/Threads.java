package email.com.gmail.ttsai0509.zetago.core.common;

public enum Threads {

    ; // Utility

    public static void sleepQuietly(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            // PIPE DOWN!
        }
    }

}
