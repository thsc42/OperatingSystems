package raceCondition;

public class Spinlock extends SyncVariant {
    private long idInCriticalSection = NO_ID;

    @Override
    public void enterCriticalSection() {
        while(this.idInCriticalSection != NO_ID) {
            // wait
            printThreadWaiting();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // ignore
            }
        }

        // block for others
        this.idInCriticalSection = Thread.currentThread().getId();

        threadCanEnterCriticalSection();
    }

    @Override
    public void leaveCriticalSection() {
        this.idInCriticalSection = NO_ID;
    }
}
