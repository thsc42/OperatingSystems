package deadlocks;

import utils.LogHelper;

public class DeadlockBinarySemaphore implements Runnable {
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
        if(!criticalSectionInUse) {
            this.criticalSectionInUse = true;
        } else {
            this.waitingThread = Thread.currentThread();
            try {
                LogHelper.printThreadLog("going to wait for free critical section");
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                LogHelper.printThreadLog("woke up call received");
            }
        }
    }

    synchronized private void up() {
        this.criticalSectionInUse = false;
        if(this.waitingThread != null) {
            this.waitingThread.interrupt(); // wake it
        }
    }

    @Override
    public void run() {
        this.down();
        this.criticalSection();
        this.up();
    }
}
