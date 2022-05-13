package readerWriter;

public class Database {
    private int a = 0;
    private int b = 0;
    private int c = 0;

    public DataSet readDataSet() {
        // make race condition more explicit
        int a = this.a;
        int b = this.b;
        int c = this.c;

        return new DataSet(a, b, c);
    }

    public void writeDataSet(int a, int b, int c) {
        // can be interrupted any time
        this.a = a;
        this.b = b;
        this.c = c;
    }
}
