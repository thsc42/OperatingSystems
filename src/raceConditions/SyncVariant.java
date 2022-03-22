package raceConditions;

import utils.LogHelper;

public abstract class SyncVariant implements SyncThreads {
    public static final long NO_ID = -1;

    public void printThreadWaiting() {
        LogHelper.printThreadLog("thread waiting");
    }

    public void threadCanEnterCriticalSection() {
        LogHelper.printThreadLog("thread can enter critical section");
    }

    public void threadLeftCriticalSection() {
        LogHelper.printThreadLog("thread left critical section");
    }

    protected void blockThread() {
        try {
            LogHelper.printThreadLog("thread blocked");
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            LogHelper.printThreadLog("thread woke up");
        }
    }
}
