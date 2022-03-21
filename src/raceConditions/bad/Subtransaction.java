package raceConditions.bad;

class Subtransaction {
    public static final long SLEEP_IN_MILLIS = 200;
    private static int nextID = 0;
    private final int id;
    private final CharSequence tag;

    private Account account;
    private Account toAccount;
    private int amount;

    Subtransaction(int amount, Account account) {
        this.amount = amount;
        this.account = account;
        this.id = Subtransaction.nextID++;
        this.tag = "transaction #" + this.id;
    }

    void perform() {
        int newBalance = this.account.getBalance(); // get balance
        System.out.println(tag + ": got balance (" + this.account.getOwner() + "): " + newBalance);

        newBalance += this.amount; // calculate new balance source
        System.out.println(tag + ": calculated new balance (" + this.account.getOwner() + "): " + newBalance);

        this.account.setBalance(newBalance); // set new balance
        System.out.println(tag + ": set new balance (" + this.account.getOwner() + "): " + newBalance);
    }

    void perform(boolean waitOne, boolean waitTwo) throws InterruptedException {

        if(waitOne) {
            System.out.println(tag + ": sleep before getting balance");
            Thread.sleep(SLEEP_IN_MILLIS);
        }

        int newBalance = this.account.getBalance(); // get balance
        System.out.println(tag + ": got balance (" + this.account.getOwner() + "): " + newBalance);

        newBalance += this.amount; // calculate new balance from
        System.out.println(tag + ": calculated new balance (" + this.account.getOwner() + "): " + newBalance);

        // to illustrate a race condition.
        if(waitTwo)  {
            System.out.println(tag + ": sleep after calculating new balance");
            Thread.sleep(SLEEP_IN_MILLIS*2);
        }

        this.account.setBalance(newBalance);
        System.out.println(tag + ": set new balance (" + this.account.getOwner() + "): " + newBalance);
    }
}
