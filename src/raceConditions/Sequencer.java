package raceConditions;

import utils.LogHelper;

public class Sequencer extends SyncVariant {
    private Thread lastThread = null;

    @Override
    public void enterCriticalSection() {
        if(this.lastThread != null) {
            try {
                Thread thread2Join;
                synchronized (this) {
                    LogHelper.printThreadLog("join last thread");
                    thread2Join = this.lastThread;
                    this.lastThread = Thread.currentThread();
                    // leave mutex to join - most likely a deadlock otherwise
                }
                thread2Join.join();
            } catch (InterruptedException e) {
                LogHelper.printThreadLog("woke up - no planned in this example");
            }
        } else {
            synchronized (this) {
                this.lastThread = Thread.currentThread();
            }
        }
    }

    @Override
    public void leaveCriticalSection() {
        // nothing to do but finishing
    }
}
