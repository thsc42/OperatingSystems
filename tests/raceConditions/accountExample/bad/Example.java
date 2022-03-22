package raceConditions.accountExample.bad;

import org.junit.Test;
import raceConditions.accountExample.bad.Account;
import raceConditions.accountExample.bad.Subtransaction;

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
    Subtransaction aliceSide1 = new Subtransaction(-100, aliceAccount); // -100
    Subtransaction bobSide1 = new Subtransaction(100, bobAccount); // 100

    // 2)
    Subtransaction aliceSide2 = new Subtransaction(100, aliceAccount); // -100
    Subtransaction bobSide2 = new Subtransaction(-100, bobAccount); // 100

    void printAccount(Account account) {
        System.out.println(account.getOwner() + ": balance == " + account.getBalance());
    }

    @Test
    public void moneyLaundry1() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> before transactions <<<<<<<<<<<<<<<<<<<<<<<<");
        this.printAccount(aliceAccount);
        this.printAccount(bobAccount);

        this.aliceSide1.perform();
        this.bobSide1.perform();

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> after transactions1 <<<<<<<<<<<<<<<<<<<<<<<<<");
        this.printAccount(aliceAccount);
        this.printAccount(bobAccount);

        this.aliceSide2.perform();
        this.bobSide2.perform();

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> after both transactions <<<<<<<<<<<<<<<<<<<<<<<<<");
        this.printAccount(aliceAccount);
        this.printAccount(bobAccount);
    }

    @Test
    public void couldBeARaceCondition() throws InterruptedException {
        SubtransactionThread transactionThreadA1 = new SubtransactionThread(this.aliceSide1);
        SubtransactionThread transactionThreadB1 = new SubtransactionThread(this.bobSide1);
        SubtransactionThread transactionThreadA2 = new SubtransactionThread(this.aliceSide2);
        SubtransactionThread transactionThreadB2 = new SubtransactionThread(this.bobSide2);

        transactionThreadA1.start();
        transactionThreadB1.start();
        transactionThreadA2.start();
        transactionThreadB2.start();

        // wait a moment in this Thread
        Thread.sleep(500);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> after transactions <<<<<<<<<<<<<<<<<<<<<<<<<");
        this.printAccount(this.aliceAccount);
        this.printAccount(this.bobAccount);
    }

    @Test
    public void myFavorite() throws InterruptedException {
        // alice -100
        SubtransactionThread transactionThreadA1 = new SubtransactionThread(this.aliceSide1, true, false);
        // alice +100
        SubtransactionThread transactionThreadA2 = new SubtransactionThread(this.aliceSide2, false, true);

        // bob +100
        SubtransactionThread transactionThreadB1 = new SubtransactionThread(this.bobSide1, false, true);
        // bob -100
        SubtransactionThread transactionThreadB2 = new SubtransactionThread(this.bobSide2, true, false);

        transactionThreadA1.start();
        transactionThreadA2.start();

        //Thread.sleep(500);
        transactionThreadB1.start();
        transactionThreadB2.start();

        // wait a moment in this Thread
        Thread.sleep(1000);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> after transactions <<<<<<<<<<<<<<<<<<<<<<<<<");
        this.printAccount(this.aliceAccount);
        this.printAccount(this.bobAccount);
    }

    private class SubtransactionThread extends Thread {
        private final Subtransaction transaction;
        private final boolean waitOne;
        private final boolean waitTwo;

        SubtransactionThread(Subtransaction transaction, boolean waitOne, boolean waitTwo) {
            this.transaction = transaction;
            this.waitOne = waitOne;
            this.waitTwo = waitTwo;
        }

        SubtransactionThread(Subtransaction transaction) {
            this(transaction, false, false);
        }

        public void run() {
            try {
                this.transaction.perform(this.waitOne, this.waitTwo);
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }
}
