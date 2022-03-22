package raceConditions;

/**
 * Bring data and methods into a single construct and declare single (process) thread usage only.
 * That is the idea of a monitor (around 1975 Hoare).
 *
 * That is OO and synchronized in this century.
 *
 */
public class CriticalSectionMonitor implements Runnable {
    private long sharedNumber;
    public static final long SLEEP_IN_MILLIS = 500;

    synchronized void criticalSection() {
        long threadID = Thread.currentThread().getId();
        System.out.println("thread entered critical section: " + threadID);

        // do something
        this.sharedNumber = threadID; // access shared value - critical thing!!

        // slow it down a bit for us humans
        try {
            Thread.sleep(SLEEP_IN_MILLIS);
        } catch (InterruptedException e) {
            System.out.println("sleep was interrupted: " + Thread.currentThread().getId());
        }

        System.out.println("thread is leaving critical section: " + threadID);
    }

    @Override
    public void run() {
        this.criticalSection();
    }
}

