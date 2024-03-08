package com.baeldung.java21;

public class SwitchPattern {
    
    class Account{
        double getBalance() {
            return 0;
        }
    }
    
    class SavingsAccount extends Account {
        double getBalance() {
            return 100;
        } 
    }
    class TermAccount extends Account {
        double getBalance() {
            return 1000;
        } 
    }
    class CurrentAccount extends Account {
        double getBalance() {
            return 10000;
        } 
    }
    
    static double getBalanceWithOutSwitchPattern(Account account) {
        double balance = 0;
        if(account instanceof SavingsAccount sa) {
            balance = sa.getBalance();
        }
        else if(account instanceof TermAccount ta) {
            balance = ta.getBalance();
        }
        else if(account instanceof CurrentAccount ca) {
            balance = ca.getBalance();
        }
        return balance;
    }
    
    static double getBalanceWithSwitchPattern(Account account) {
        double result = 0;
        switch (account) {
            case null -> throw new RuntimeException("Oops, account is null");
            case SavingsAccount sa -> result = sa.getBalance();
            case TermAccount ta -> result = ta.getBalance();
            case CurrentAccount ca -> result = ca.getBalance();
            default -> result = account.getBalance();
        };
        return result;
    }
}
