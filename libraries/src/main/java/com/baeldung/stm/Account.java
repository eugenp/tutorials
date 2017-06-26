package com.baeldung.stm;

import org.multiverse.api.StmUtils;
import org.multiverse.api.callables.TxnCallable;
import org.multiverse.api.references.TxnInteger;
import org.multiverse.api.references.TxnLong;

public class Account {

    private final TxnLong lastUpdate;
    private final TxnInteger balance;

    public Account(final int balance) {
        this.lastUpdate = StmUtils.newTxnLong(System.currentTimeMillis());
        this.balance = StmUtils.newTxnInteger(balance);
    }

    public Integer getBalance() {
        return balance.atomicGet();
    }

    public void adjustBy(final int amount) {
        adjustBy(amount, System.currentTimeMillis());
    }

    public void adjustBy(final int amount, final long date) {
        StmUtils.atomic(() -> {
            balance.increment(amount);
            lastUpdate.set(date);

            if (balance.get() < 0) {
                throw new IllegalArgumentException("Not enough money");
            }
        });
    }

    public void transferTo(final Account other, final int amount) {
        StmUtils.atomic(() -> {
            final long date = System.currentTimeMillis();
            adjustBy(-amount, date);
            other.adjustBy(amount, date);
        });
    }

    @Override
    public String toString() {
        return StmUtils.atomic((TxnCallable<String>)
                txn -> "Balance: " + balance.get(txn) + " lastUpdateDate: " + lastUpdate.get(txn));
    }
}