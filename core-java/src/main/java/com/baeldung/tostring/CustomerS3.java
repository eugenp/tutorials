package com.baeldung.tostring;

import java.util.Arrays;

public class CustomerS3  {
	private String firstName;
    private String lastName;
    private long balance;
    private Order[] orders;
 
    public Order[] getOrders() {
        return orders;
    }
    public void setOrders(Order[] orders) {
        this.orders = orders;
    }    
    public long getBalance() {
        return balance;
    }
    public void setBalance(long balance) {
        this.balance = balance;
    }
    
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Override
    public String toString() {
        return "Customer [firstName=" + firstName + ", lastName=" + lastName + ", balance=" + balance + ", orders=" + Arrays.toString(orders) + "]";
    }
}