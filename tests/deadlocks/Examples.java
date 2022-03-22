package deadlocks;

import org.junit.Test;

public class Examples {
    @Test
    public void wrongSemaphore() throws InterruptedException {
        DeadlockBinarySemaphore deadlockBinarySemaphore = new DeadlockBinarySemaphore();
        Thread one = new Thread(deadlockBinarySemaphore);
        Thread two = new Thread(deadlockBinarySemaphore);
        one.start();
        two.start();
        Thread.sleep(2000);
        System.err.println("only first thread entered critical section. Why?");
    }
}
