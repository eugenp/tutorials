package com.baeldung;

import com.baeldung.model.Account;
import com.baeldung.model.Customer;

public class ShallowDeepCopy {
    public static void main(String[] args) {
        Account originalAccount = new Account("112233", 1000.1);
        Customer originalCustomer = new Customer("Baeldung", 30, originalAccount);

        //Creating Shallow Copy
        Customer shallowCopyCustomer = new Customer(originalCustomer.getName(), originalCustomer.getAge(), originalCustomer.getAccount());

        //Creating Deep Copy
        Customer deepCopyCustomer = new Customer(originalCustomer);

        // Modifying the city in the original address
        originalAccount.setBalance(500.1);

        // Printing Customer data
        System.out.println("Original Customer: \nName: " + originalCustomer.getName() + ",\nAge: " + originalCustomer.getAge() + ",\nAccount Number: " + originalCustomer.getAccount().getAccountNumber() + ", Balance: " + originalCustomer.getAccount().getBalance());
        System.out.println("\nShallow Copy Customer: \nName: " + shallowCopyCustomer.getName() + ",\nAge: " + shallowCopyCustomer.getAge() + ",\nAccount Number: " + shallowCopyCustomer.getAccount().getAccountNumber() + ", Balance: " + shallowCopyCustomer.getAccount().getBalance());
        System.out.println("\nDeep Copy Customer: \nName: " + deepCopyCustomer.getName() + ",\nAge: " + deepCopyCustomer.getAge() + ",\nAccount Number: " + deepCopyCustomer.getAccount().getAccountNumber() + ", Balance: " + deepCopyCustomer.getAccount().getBalance());

    }
}