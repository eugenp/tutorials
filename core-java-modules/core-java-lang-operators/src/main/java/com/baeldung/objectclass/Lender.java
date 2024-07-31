package com.baeldung.objectclass;

public class Lender extends User {
    
    private double totalInvestmentAmount;
    
    public double invest(double amount) {
        totalInvestmentAmount = amount;
        return totalInvestmentAmount;
    }
    
    public double increaseInvestment(double increaseBy) {
        return totalInvestmentAmount + increaseBy;
    }
    
    public double collectDividends() {
        return totalInvestmentAmount * 0.07;
    }

}
