package com.baeldung.stackoverflowerror;


public class AccountHolder {
    
    private String firstName = "Sam";
    private String lastName = "Alex"; 
    
    AccountHolder jointAccountHolder = new AccountHolder();
    
    public static void main(String[] args) {
        AccountHolder holder = new AccountHolder();
        System.out.println("Account Holder Name : " + holder.firstName + " " 
        + holder.lastName);
        System.out.println("Joint Account Holder Name : " 
        + holder.jointAccountHolder.firstName + " " + holder.jointAccountHolder.lastName);
    }
}
