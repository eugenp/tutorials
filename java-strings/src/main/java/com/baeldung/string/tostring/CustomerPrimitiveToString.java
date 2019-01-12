package com.baeldung.string.tostring;

public class CustomerPrimitiveToString extends Customer {
    private long balance;
 
    public long getBalance() {
        return balance;
    }
	
    public void setBalance(long balance) {
        this.balance = balance;
    }
        
    @Override
    public String toString() {
	return "Customer [balance=" + balance + ", getFirstName()=" + getFirstName()
	  + ", getLastName()=" + getLastName() + "]";
    }
}
