package com.baeldung.java21;

public class SwitchPattern {

    private SwitchPattern(){}
    
    static class Account{
        double getBalance() {
            return 0;
        }
    }
    
    static class SavingsAccount extends Account {
        double getSavings() {
            return 100;
        } 
    }
    
    static class TermAccount extends Account {
        double getTermAccount() {
            return 1000;
        } 
    }
    static class CurrentAccount extends Account {
        double getCurrentAccount() {
            return 10000;
        } 
    }
    
    static double getBalanceWithOutSwitchPattern(Account account) {
        double balance = 0;
        if(account instanceof SavingsAccount sa) {
            balance = sa.getSavings();
        }
        else if(account instanceof TermAccount ta) {
            balance = ta.getTermAccount();
        }
        else if(account instanceof CurrentAccount ca) {
            balance = ca.getCurrentAccount();
        }
        return balance;
    }
    
    static double getBalanceWithSwitchPattern(Account account) {
        double result;
        switch (account) {
            case null -> throw new IllegalArgumentException("Oops, account is null");
            case SavingsAccount sa -> result = sa.getSavings();
            case TermAccount ta -> result = ta.getTermAccount();
            case CurrentAccount ca -> result = ca.getCurrentAccount();
            default -> result = account.getBalance();
        }
        return result;
    }
}
