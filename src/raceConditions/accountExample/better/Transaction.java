package raceConditions.accountExample.better;

class Transaction {
    public static final long SLEEP_IN_MILLIS = 200;
    private static int nextID = 0;
    private final int id;
    private final Account accountTo;
    private final Account accountFrom;

    private int amount;

    Transaction(int amount, Account accountFrom, Account accountTo) {
        this.amount = amount;
        this.accountTo = accountTo;
        this.accountFrom = accountFrom;
        this.id = Transaction.nextID++;
    }

    // that's a critical section - declare it as such and make it thread safe.
    synchronized void perform() {
        this.accountFrom.add(-this.amount);
        this.accountTo.add(this.amount);
    }
}
