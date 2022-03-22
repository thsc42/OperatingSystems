package raceConditions;

import utils.LogHelper;

public class CriticalSectionExample implements Runnable {
    private final SyncThreads sync;
    private long sharedNumber;
    public static final long SLEEP_IN_MILLIS = 500;

    public CriticalSectionExample(SyncThreads sync) {
        this.sync = sync;
    }

    void criticalSection() {
        long threadID = Thread.currentThread().getId();
        LogHelper.printThreadLog("thread entered critical section");

        // do something
        this.sharedNumber = threadID; // access shared value - critical thing!!

        // slow it down a bit for us humans
        try {
            Thread.sleep(SLEEP_IN_MILLIS);
        } catch (InterruptedException e) {
            LogHelper.printThreadLog("sleep interrupted (not planned in this example");
        }

        LogHelper.printThreadLog("thread is leaving critical section");
    }

    @Override
    public void run() {
        this.sync.enterCriticalSection();
        this.criticalSection();
        this.sync.leaveCriticalSection();

    }
}
