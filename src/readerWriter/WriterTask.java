package readerWriter;

import utils.LogHelper;

public class WriterTask implements Runnable {
    private final Database db;

    public WriterTask(Database db) {
        this.db = db;
    }

    public void run() {
        // read from data base
        LogHelper.printThreadLog("going to write...");
        this.db.writeDataSet(42, 43, 44);
        LogHelper.printThreadLog("...wrote");
    }
}
