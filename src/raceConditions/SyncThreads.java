package raceConditions;

public interface SyncThreads {
    void enterCriticalSection();

    void leaveCriticalSection();
}
