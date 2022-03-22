package raceConditions;

import java.util.ArrayList;
import java.util.List;

public class SleepWakeup extends SyncVariant {
    private List<Thread> waitingThread = new ArrayList<>();
    private boolean criticalSectionUsed = false;

    @Override
    public void enterCriticalSection() {
        while(criticalSectionUsed) {
            this.waitingThread.add(Thread.currentThread());
            this.blockThread();
        }

        this.criticalSectionUsed = true; // still a race condition!
    }

    @Override
    public void leaveCriticalSection() {
        this.criticalSectionUsed = false;
        if(!this.waitingThread.isEmpty()) {
            Thread firstThread = this.waitingThread.remove(0);
            firstThread.interrupt(); // wake up
        }
    }
}
