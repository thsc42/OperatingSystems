package readerWriter;

import org.junit.Test;

public class Scenarios {
    public static final int READER_THREADS_NUMBER = 10;
    @Test
    public void manyReaders() throws InterruptedException {
        Database db = new Database();
        Thread[] readerThreads = new Thread[READER_THREADS_NUMBER];
        for(int i = 0; i < READER_THREADS_NUMBER; i++) {
            readerThreads[i] = new Thread(new ReaderTask(db));
        }

        // launch
        for(int i = 0; i < READER_THREADS_NUMBER; i++) {
            readerThreads[i].start();
        }

        // let unit test sleep a moment
        Thread.sleep(1000);
    }
}
