package com.baeldung.java_shallow_deep_copy.unit;

import com.baeldung.java_shallow_deep_copy.data.Balance;
import com.baeldung.java_shallow_deep_copy.data.BankAccountShallow;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ShallowCopyTest {

    @Test
    void whenIsAShallowCopyDoneByCopyConstructor_thenImmutableObjectWillNotChange() {
        String name = "Hello";
        String surname = "World";
        BankAccountShallow personShallow = new BankAccountShallow(name, surname, null);
        surname = "Pluto";
        assertNotEquals(surname, personShallow.getSurname());
    }

    @Test
    void whenIsAShallowCopyDoneByCopyConstructor_thenNestedObjectsAreTheSame() {
        Balance balance = new Balance(10000, "EUR");
        BankAccountShallow bankAccount = new BankAccountShallow("Hello", "World", balance);
        BankAccountShallow shallowCopy = new BankAccountShallow(bankAccount.getName(), bankAccount.getSurname(), bankAccount.getBalance());
        assertEquals(bankAccount.getBalance(), shallowCopy.getBalance());
    }

    @Test
    void whenIsAShallowCopyDoneByCopyConstructor_thenCopyValueShouldChange() throws CloneNotSupportedException {
        Balance balance = new Balance(10000, "EUR");
        BankAccountShallow bankAccount = new BankAccountShallow("Hello", "World", balance);
        BankAccountShallow shallowCopy = new BankAccountShallow(bankAccount);
        float newBalance = 0;
        balance.setAmount(newBalance);
        assertEquals(newBalance, bankAccount.getBalance()
          .getAmount());
        assertEquals(newBalance, shallowCopy.getBalance()
          .getAmount());
    }

    @Test
    void whenIsAShallowCopyDoneByCloneMethod_thenNestedObjectsAreTheSame() throws CloneNotSupportedException {
        Balance balance = new Balance(10000, "EUR");
        BankAccountShallow bankAccount = new BankAccountShallow("Hello", "World", balance);
        BankAccountShallow shallowCopy = (BankAccountShallow) bankAccount.clone();
        assertEquals(bankAccount.getBalance(), shallowCopy.getBalance());
    }

    @Test
    void whenIsAShallowCopyDoneByCloneMethod__thenCopyValueShouldChange() throws CloneNotSupportedException {
        Balance balance = new Balance(10000, "EUR");
        BankAccountShallow bankAccount = new BankAccountShallow("Hello", "World", balance);
        BankAccountShallow shallowCopy = (BankAccountShallow) bankAccount.clone();
        float newBalance = 0;
        balance.setAmount(newBalance);
        assertEquals(newBalance, shallowCopy.getBalance()
          .getAmount());
    }

}
