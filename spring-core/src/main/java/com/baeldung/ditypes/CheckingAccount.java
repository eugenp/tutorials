package com.baeldung.ditypes;

public class CheckingAccount {
    
    private Transaction debitTransaction;
    private Transaction creditTransaction;
    
    public CheckingAccount(Transaction debitTransaction) {
        this.debitTransaction = debitTransaction;
    }  
    
    public void setCreditTransaction(Transaction creditTransaction) {
        this.creditTransaction = creditTransaction;
    }
    
    public String processDebitTransaction() {
       return this.debitTransaction.process();
    }
    
    public String processCreditTransaction() {
        return this.creditTransaction.process();
    }
}
