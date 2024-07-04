package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void testShallowCopy() throws CloneNotSupportedException {
        TransactionHistory history = new TransactionHistory();
        BankAccount original = new BankAccount(500.0, history);
        original.deposit(200.0);  // Make a deposit which logs to the transaction history

        // Perform a shallow copy of the original bank account
        BankAccount shallowCopy = original.shallowClone();
        shallowCopy.deposit(300.0); // Add another transaction to the shallow copy

        // Verify balances
        assertEquals(700.0, original.getBalance(), 0.0);
        assertEquals(1000.0, shallowCopy.getBalance(), 0.0);
        
        // Verify the transaction histories 
        String expectedOriginalTransactions = "Deposit: 200.00\n";
        String expectedShallowCopyTransactions = "Deposit: 200.00\nDeposit: 300.00\n";      
        assertNotEquals(expectedOriginalTransactions, original.getTransactionHistory());
        assertEquals(expectedShallowCopyTransactions, shallowCopy.getTransactionHistory());
        assertEquals(original.getTransactionHistory(), shallowCopy.getTransactionHistory());
    }

    @Test
    void testDeepCopy() throws CloneNotSupportedException {
        TransactionHistory history = new TransactionHistory();
        BankAccount original = new BankAccount(500.0, history);
        original.deposit(200.0);  // Make a deposit which logs to the transaction history

        // Perform a deep copy of the original bank account
        BankAccount deepCopy = original.clone();
        deepCopy.deposit(300.0); // Add another transaction to the deep copy

        // Verify balances
        assertEquals(700.0, original.getBalance(), 0.0);
        assertEquals(1000.0, deepCopy.getBalance(), 0.0);
       
        // Verify that the transaction histories are different (not shared)
        String expectedOriginalTransactions = "Deposit: 200.00\n";
        String expectedDeepCopyTransactions = "Deposit: 200.00\nDeposit: 300.00\n";
        assertEquals(expectedOriginalTransactions, original.getTransactionHistory());
        assertEquals(expectedDeepCopyTransactions, deepCopy.getTransactionHistory());
        assertNotEquals(original.getTransactionHistory(), deepCopy.getTransactionHistory());
    }
}
