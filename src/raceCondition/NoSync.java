package raceCondition;

public class NoSync extends SyncVariant {
    @Override
    public void enterCriticalSection() {
        // do not do anything
    }

    @Override
    public void leaveCriticalSection() {
        // do not do anything
    }
}
