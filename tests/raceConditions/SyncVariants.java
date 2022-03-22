package raceConditions;

import org.junit.Test;
import raceConditions.*;

public class SyncVariants {
    public Thread[] setUpThread(SyncThreads mutex, int number) {
        Thread parallelThreads[] = new Thread[number];
        for(int i = 0; i < number; i++) {
            CriticalSectionExample example = new CriticalSectionExample(mutex);// same mutex in any instance
            parallelThreads[i] = new Thread(example); // create a thread that will run example's code
        }

        return parallelThreads;
    }

    public void launchThreads(Thread[] parallelThreads, boolean delayedStart) throws InterruptedException {
        for(int i = 0; i < parallelThreads.length; i++) {
            parallelThreads[i].start();
            if(delayedStart) Thread.sleep(1);
        }
        // wait a moment to let thread do their job
        Thread.sleep(1000);
    }

    @Test
    public void noMutex() throws InterruptedException {
        System.out.println("no syncer");
        SyncThreads syncer = new NoSync(); // one syncer only
        Thread[] parallelThreads = this.setUpThread(syncer, 5);
        this.launchThreads(parallelThreads, false);
    }

    @Test
    public void spinlock() throws InterruptedException {
        System.out.println("spinlock");
        SyncThreads syncer = new Spinlock(); // one syncer only
        Thread[] parallelThreads = this.setUpThread(syncer, 2);
        this.launchThreads(parallelThreads, false); // still a race condition
//        this.launchThreads(parallelThreads, true); // better - but does not solve the problem
    }

    @Test
    public void sleepWakeup() throws InterruptedException {
        System.out.println("sleep / wakeup");
        SyncThreads syncer = new SleepWakeup(); // one syncer only
        Thread[] parallelThreads = this.setUpThread(syncer, 10);
        this.launchThreads(parallelThreads, true); // still a race condition
        Thread.sleep(5000);
    }

    @Test
    public void semaphore() throws InterruptedException {
        System.out.println("semaphore");
        SyncThreads syncer = new Semaphore(3); // one syncer only
        Thread[] parallelThreads = this.setUpThread(syncer, 10);
        this.launchThreads(parallelThreads, false); // thanks to synchronized no more race conditions
        Thread.sleep(3000);
    }


    @Test
    public void sequencer() throws InterruptedException {
        System.out.println("sequencer");
        SyncThreads syncer = new Sequencer(); // one syncer only
        Thread[] parallelThreads = this.setUpThread(syncer, 10);
        this.launchThreads(parallelThreads, true); // still race condition...run it with false and observe
        Thread.sleep(3000);
    }

    @Test
    public void mutexWhyWrongCode() throws InterruptedException {
        int number = 10;
        System.out.println("bad mutex");
        Thread parallelThreads[] = new Thread[number];
        for(int i = 0; i < number; i++) {
            CriticalSectionMutex example = new CriticalSectionMutex();
            parallelThreads[i] = new Thread(example); // create a thread that will run example's code
        }
        this.launchThreads(parallelThreads, false);
        Thread.sleep(3000);
        System.err.println("all threads entered critical section simultaneously. Why?");
    }

    @Test
    public void mutex() throws InterruptedException {
        int number = 10;
        System.out.println("mutex");
        CriticalSectionMutex example = new CriticalSectionMutex();
        Thread parallelThreads[] = new Thread[number];
        for(int i = 0; i < number; i++) {
            parallelThreads[i] = new Thread(example); // create a thread that will run example's code
        }
        this.launchThreads(parallelThreads, false);
        Thread.sleep(3000);
    }
}
