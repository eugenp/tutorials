package com.baeldung.fitnesse;

public class BankAccountFixture {

    private BankAccount account;
    private double depositAmount;
    private double withdrawAmount;

    public void setInitialBalance(double balance) {
        account = new BankAccount(balance);
    }

    public void setDeposit(double amount) {
        this.depositAmount = amount;
    }

    public void setWithdraw(double amount) {
        this.withdrawAmount = amount;
    }

    public void execute() {
        if (depositAmount > 0) {
            account.deposit(depositAmount);
        }
        if (withdrawAmount > 0) {
            account.withdraw(withdrawAmount);
        }
    }

    public double balance() {
        return account.getBalance();
    }
}
