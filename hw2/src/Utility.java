import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Berker Acir
 */
public abstract class Utility {

    static void threadSleep(int interval) {
        try {
            Thread.sleep(interval + (long) 0.01* ThreadLocalRandom.current().nextLong(-interval, interval));
        }
        catch (InterruptedException ex) { }
    }
}
