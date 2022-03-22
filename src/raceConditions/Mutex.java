package raceConditions;

/**
 * Only one thread can enter critical section. Next thread is chosen by change afterwards.
 * Java keyword synchronized makes the trick
 */
public class Mutex extends SyncVariant {
    @Override
    public synchronized void enterCriticalSection() {

    }

    @Override
    public synchronized void leaveCriticalSection() {

    }
}
