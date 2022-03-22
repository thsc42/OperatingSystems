package raceConditions.accountExample.bad;

class Account {
    private final CharSequence owner;
    private int balance = 0;

    Account(CharSequence owner) {
        this.owner = owner;
    }

    CharSequence getOwner() { return this.owner; }

    int getBalance() {
        return this.balance;
    }

    void setBalance(int newBalance) {
        this.balance = newBalance;
    }
}
