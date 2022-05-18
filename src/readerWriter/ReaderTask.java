package readerWriter;

import utils.LogHelper;

public class ReaderTask implements Runnable {
    private final Database db;
    public ReaderTask(Database db) {
        this.db = db;
    }
    public void run() {
        // read from data base
        LogHelper.printThreadLog("going to read...");
        DataSet result = this.db.readDataSet();
        StringBuilder sb = new StringBuilder();
        sb.append("... read from db: a == "); sb.append(result.a);
        sb.append(" | b == "); sb.append(result.b);
        sb.append(" | c == "); sb.append(result.c);
        LogHelper.printThreadLog(sb.toString());
    }
}
