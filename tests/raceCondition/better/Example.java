package raceCondition.better;
import org.junit.Test;

public class Example {
    public static final CharSequence ALICE = "Alice";
    public static final CharSequence BOB = "Bob";

    // two empty accounts: two transactions:
    // 1) Alice transmits 100 to Bob
    // 2) back
    Account aliceAccount = new Account(ALICE);
    Account bobAccount = new Account(BOB);

    // each transaction is split into two sub transactions:
    // 1)
    Transaction alice2Bob = new Transaction(100, aliceAccount, bobAccount);
    Transaction bob2Alice = new Transaction(100, bobAccount, aliceAccount);

    void printAccount(Account account) {
        System.out.println(account.getOwner() + ": balance == " + account.getBalance());
    }

    @Test
    public void noRaceCondition() throws InterruptedException {
        TransactionThread transactionThread1 = new TransactionThread(alice2Bob);
        TransactionThread transactionThread2 = new TransactionThread(bob2Alice);

        transactionThread1.start();
        transactionThread2.start();

        // wait a moment in this Thread
        Thread.sleep(500);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> after transactions <<<<<<<<<<<<<<<<<<<<<<<<<");
        this.printAccount(this.aliceAccount);
        this.printAccount(this.bobAccount);
    }

    private class TransactionThread extends Thread {
        private final Transaction transaction;

        TransactionThread(Transaction transaction) {
            this.transaction = transaction;
        }
        public void run() {
            this.transaction.perform();
        }
    }
}