package raceConditions.accountExample.better;

class Account {
    private final CharSequence owner;
    private int balance = 0;

    Account(CharSequence owner) {
        this.owner = owner;
    }

    CharSequence getOwner() { return this.owner; }

    // synchronized (not really necessary with a single line of code - doesn't hurt anyway)
    synchronized void add(int amount) {
        this.balance += amount;
    }

     int getBalance() {
        return this.balance;
    }

/*   setter is a bad idea in that case
    void setBalance(int newBalance) {
        this.balance = newBalance;
    }
 */

}
