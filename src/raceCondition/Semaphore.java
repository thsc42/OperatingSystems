package raceCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * Dijkstra generalized the sleep / wakeup version in 1965 (see Tanenbaum). He called those methods up and down.
 * Important thing: Up() and down() must be <b>atomic actions</b> Can and must be provided by operating system.
 */
public class Semaphore extends SyncVariant {
    private List<Thread> waitingThread = new ArrayList<>();
    private final int maxNumberInCriticalSection;
    private int freePlaces;

    public Semaphore(int maxNumberInCriticalSection) {
        this.maxNumberInCriticalSection = maxNumberInCriticalSection;
        this.freePlaces = maxNumberInCriticalSection;
    }

    /**
     * synchronized makes it atomic - but thread must leave this method
      * we have to implement it slightly different from original Dijkstra.
     */
    synchronized private boolean down() {
        if(this.freePlaces > 0) {
            this.freePlaces--;
            return true;
        }

        return false;
    }

    synchronized private void up() {
        this.freePlaces++;
    }

    @Override
    public void enterCriticalSection() {
        if(!this.down()) {
            this.waitingThread.add(Thread.currentThread());
            this.blockThread();
        }
    }

    @Override
    public void leaveCriticalSection() {
        this.up();
        if(!this.waitingThread.isEmpty()) {
            Thread firstThread = this.waitingThread.remove(0);
            firstThread.interrupt(); // wake up
        }
    }
}
