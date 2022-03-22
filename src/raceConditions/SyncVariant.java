package raceConditions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class SyncVariant implements SyncThreads {
    public static final long NO_ID = -1;

    private String getExactTime() {
        long now = System.currentTimeMillis();

        Date date = new Date(now);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        return df.format(date);

    }

    protected void printLog(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getExactTime());
        sb.append(" (Thread #");
        sb.append(Thread.currentThread().getId());
        sb.append("): ");
        sb.append(message);
        System.out.println(sb.toString());
    }

    public void printThreadWaiting() {
        this.printLog("thread waiting");
    }

    public void threadCanEnterCriticalSection() {
        this.printLog("thread can enter critical section");
    }

    public void threadLeftCriticalSection() {
        this.printLog("thread left critical section");
    }

    protected void blockThread() {
        try {
            this.printLog("thread blocked");
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            this.printLog("thread woke up");
        }
    }
}
