package raceConditions;

import utils.LogHelper;

public class Monitor implements Runnable {
    private Thread waitingThread = null;

    private void criticalSection() {
        LogHelper.printThreadLog("entered critical section");

        try {
            Thread.sleep(100); // would do something critical
        } catch (InterruptedException e) {
            // ignore
        }

        LogHelper.printThreadLog("leaving critical section");
    }

    private boolean criticalSectionInUse = false;

    synchronized private void down() {
        if (!criticalSectionInUse) {
            this.criticalSectionInUse = true;
        } else {
            this.waitingThread = Thread.currentThread();
            try {
                LogHelper.printThreadLog("going to wait for free critical section");
                this.wait();
            } catch (InterruptedException e) {
                LogHelper.printThreadLog("woke up call received");
            }
        }
    }

    synchronized private void up() {
        this.criticalSectionInUse = false;
        if (this.waitingThread != null) {
            this.notify(); // notify waiting threads in monitor
        }
    }

    @Override
    public void run() {
        this.down();
        this.criticalSection();
        this.up();
    }
}