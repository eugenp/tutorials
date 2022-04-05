package com.baeldung.objectclass;

import lombok.Data;

@Data
public class Borrower extends User {
    
    private double totalLoanAmount;
    
    public double requestLoan(double amount) {
        totalLoanAmount = amount;
        return totalLoanAmount;
    }
    
    public double increaseLoan(double increaseBy) {
        return totalLoanAmount + increaseBy;
    }
    
    public double payLoan(double amount) {
        return totalLoanAmount - amount;
    }

}
