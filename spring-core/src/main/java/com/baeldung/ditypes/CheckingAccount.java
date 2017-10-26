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
    
    public void processTransaction() {
       System.out.println(this.debitTransaction.process());
       System.out.println(this.creditTransaction.process());
    }
}
