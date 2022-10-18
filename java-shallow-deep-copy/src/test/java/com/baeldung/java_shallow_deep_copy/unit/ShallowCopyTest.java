package com.baeldung.java_shallow_deep_copy.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import com.baeldung.java_shallow_deep_copy.data.Balance;
import com.baeldung.java_shallow_deep_copy.data.BankAccountShallow;

public class ShallowCopyTest {

    @Test
    void whenIsAShallowCopyDoneByCopyConstructor_thenImmutableObjectWillNotChange() {
        BankAccountShallow bankAccount = new BankAccountShallow("Hello", "World", null);
        BankAccountShallow shallowCopy = new BankAccountShallow(bankAccount);
        bankAccount.setSurname("Pluto");
        assertNotEquals("Pluto", shallowCopy.getSurname());
    }

    @Test
    void whenIsAShallowCopyDoneByCopyConstructor_thenNestedObjectsAreTheSame() {
        Balance balance = new Balance(10000, "EUR");
        BankAccountShallow bankAccount = new BankAccountShallow("Hello", "World", balance);
        BankAccountShallow shallowCopy = new BankAccountShallow(bankAccount.getName(), bankAccount.getSurname(), bankAccount.getBalance());
        assertEquals(bankAccount.getBalance(), shallowCopy.getBalance());
    }

    @Test
    void whenIsAShallowCopyDoneByCopyConstructor_thenCopyShouldChange() {
        Balance balance = new Balance(10000, "EUR");
        BankAccountShallow bankAccount = new BankAccountShallow("Hello", "World", balance);
        BankAccountShallow shallowCopy = new BankAccountShallow(bankAccount);
        bankAccount.getBalance()
          .setAmount(0);
        float shallowCopyAmount = shallowCopy.getBalance()
          .getAmount();
        assertEquals(0, shallowCopyAmount);
    }

    @Test
    void whenIsAShallowCopyDoneByCloneMethod_thenNestedObjectsAreTheSame() throws CloneNotSupportedException {
        Balance balance = new Balance(10000, "EUR");
        BankAccountShallow bankAccount = new BankAccountShallow("Hello", "World", balance);
        BankAccountShallow shallowCopy = (BankAccountShallow) bankAccount.clone();
        assertEquals(bankAccount.getBalance(), shallowCopy.getBalance());
    }

    @Test
    void whenIsAShallowCopyDoneByCloneMethod__thenCopyShouldChange() throws CloneNotSupportedException {
        Balance balance = new Balance(10000, "EUR");
        BankAccountShallow bankAccount = new BankAccountShallow("Hello", "World", balance);
        BankAccountShallow shallowCopy = (BankAccountShallow) bankAccount.clone();
        bankAccount.getBalance()
          .setAmount(0);
        float shallowCopyAmount = shallowCopy.getBalance()
          .getAmount();
        assertEquals(0, shallowCopyAmount);
    }

}
