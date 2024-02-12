package com.baeldung;

import com.baeldung.model.Account;
import com.baeldung.model.Bank;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ShallowDeepCopyArrayListTest {

    @Test
    public void when_modifying_original_object_then_shallow_copy_should_change() {
        Bank originalBank = new Bank("Baeldung Bank");
        originalBank.addAccount(new Account("001", 1000));
        originalBank.addAccount(new Account("002", 2000));

        Bank shallowCopyBank = new Bank(originalBank.getName());
        shallowCopyBank.getAccounts().addAll(originalBank.getAccounts());

        // Modifying balance in the original account
        originalBank.getAccounts().get(0).setBalance(500);

        // Assert that both objects now share the same modified data
        assertEquals(originalBank.getAccounts(), shallowCopyBank.getAccounts());
    }

    @Test
    public void when_modifying_original_customer_then_deep_copy_should_not_change() {
        Bank originalBank = new Bank("Baeldung Bank");
        originalBank.addAccount(new Account("001", 1000));
        originalBank.addAccount(new Account("002", 2000));

        Bank deepCopyBank = new Bank(originalBank.getName());
        originalBank.getAccounts().forEach(acc-> deepCopyBank.addAccount(new Account(acc.getAccountNumber(), acc.getBalance())));

        // Modifying balance in the original account
        originalBank.getAccounts().get(0).setBalance(800.0);

        // Assert that deep-copied object has an independent copy of data
        assertNotEquals(originalBank.getAccounts(), deepCopyBank.getAccounts());
    }

@Test
public void when_modifying_original_customer_then_deep_copy_should_not_change_when_using_cloneable_interface() {
    Bank originalBank = new Bank("Baeldung Bank");
    originalBank.addAccount(new Account("001", 1000));
    originalBank.addAccount(new Account("002", 2000));

    Bank deepCopyBank = (Bank) originalBank.clone();

    //Modifying Original
    originalBank.getAccounts().get(0).setBalance(800.0);

    // Assert that deep-copied object has an independent copy of data
    assertNotEquals(originalBank.getAccounts(), deepCopyBank.getAccounts());
}

}