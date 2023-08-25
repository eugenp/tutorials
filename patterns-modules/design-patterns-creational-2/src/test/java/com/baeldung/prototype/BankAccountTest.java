package com.baeldung.prototype;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.prototype.Account.AccountType;

class BankAccountTest {

    private List<AccountType> accountTypes;
    
    @BeforeEach
    public void setup() {
      accountTypes = new ArrayList<AccountType>(Arrays.asList(AccountType.values()));
    }
    
    /**
     * Method to Test Shallow Copy of Objects
     */
    @Test
    void testShallowCopy() {
        Account accountType = new Account(accountTypes);
        BankAccount accountOne = new BankAccount(1, AccountType.SAVINGS, accountType);
        BankAccount accountTwo = new BankAccount(accountOne.getAccountId(), accountOne.getAccountName(),accountOne.getAccountType());

        assertEquals(accountOne.getAccountType().getAccountTypes(), accountTwo.getAccountType().getAccountTypes());
        assertTrue(accountOne.getAccountType().getAccountTypes().contains(AccountType.LOAN));
        assertTrue(accountTwo.getAccountType().getAccountTypes().contains(AccountType.LOAN)); 
        
        accountTwo.getAccountType().getAccountTypes().remove(AccountType.LOAN);
        
        assertEquals(accountOne.getAccountType().getAccountTypes(), accountTwo.getAccountType().getAccountTypes());
        assertFalse(accountOne.getAccountType().getAccountTypes().contains(AccountType.LOAN));
        assertFalse(accountTwo.getAccountType().getAccountTypes().contains(AccountType.LOAN)); 
        
    }
    
    /**
     * Method to Test Deep Copy of Objects
     */
    @Test
    void testDeepCopy() {
        Account accountType = new Account(accountTypes);
        BankAccount accountOne = new BankAccount(1, AccountType.SAVINGS, accountType);
        BankAccount accountTwo = new BankAccount(accountOne.getAccountId(), accountOne.getAccountName(), new Account(new ArrayList<AccountType>(accountOne.getAccountType().getAccountTypes())));

        assertEquals(accountOne.getAccountType().getAccountTypes(), accountTwo.getAccountType().getAccountTypes());
        assertTrue(accountOne.getAccountType().getAccountTypes().contains(AccountType.LOAN));
        assertTrue(accountTwo.getAccountType().getAccountTypes().contains(AccountType.LOAN)); 
        
        accountTwo.getAccountType().getAccountTypes().remove(AccountType.LOAN);
        
        assertNotEquals(accountOne.getAccountType().getAccountTypes(), accountTwo.getAccountType().getAccountTypes());
        assertTrue(accountOne.getAccountType().getAccountTypes().contains(AccountType.LOAN));
        assertFalse(accountTwo.getAccountType().getAccountTypes().contains(AccountType.LOAN)); 
        
    }
}
