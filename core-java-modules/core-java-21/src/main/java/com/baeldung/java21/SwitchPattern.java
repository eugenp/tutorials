package com.baeldung.java21;

public class SwitchPattern {
    
    static class Account{
        double getBalance() {
            return 0;
        }
    }
    
    static class SavingsAccount extends Account {
        @Override
        double getBalance() {
            return 100;
        } 
    }
    
    static class TermAccount extends Account {
        @Override
        double getBalance() {
            return 1000;
        } 
    }
    static class CurrentAccount extends Account {
        @Override
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
        double result;
        switch (account) {
            case null -> throw new IllegalArgumentException("Oops, account is null");
            case SavingsAccount sa -> result = sa.getBalance();
            case TermAccount ta -> result = ta.getBalance();
            case CurrentAccount ca -> result = ca.getBalance();
            default -> result = account.getBalance();
        }
        return result;
    }
}
