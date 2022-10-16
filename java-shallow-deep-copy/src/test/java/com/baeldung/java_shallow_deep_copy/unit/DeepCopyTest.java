package com.baeldung.java_shallow_deep_copy.unit;

import com.baeldung.java_shallow_deep_copy.data.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DeepCopyTest {

    private static final String NAME = "Hello";
    private static final String SURNAME = "World";

    @Test
    void whenIsADeepCopyDoneByCopyConstructor_thenNestedObjectShouldNotHaveSameReference() {
        Balance balance = new Balance(10000, "EUR");
        BankAccountDeep bankAccount = new BankAccountDeep(NAME, SURNAME, balance);
        BankAccountDeep deepCopy = new BankAccountDeep(bankAccount);
        assertNotEquals(bankAccount.getBalance(), deepCopy.getBalance());
    }

    @Test
    void whenIsADeepCopyDoneByCopyConstructor_thenCopyValueShouldNotChange() {
        Balance balance = new Balance(10000, "EUR");
        BankAccountDeep bankAccount = new BankAccountDeep(NAME, SURNAME, balance);
        BankAccountDeep deepCopy = new BankAccountDeep(bankAccount);
        balance.setAmount(0);
        assertNotEquals(bankAccount.getBalance()
          .getAmount(), deepCopy.getBalance()
          .getAmount());
    }

    @Test
    void whenIsADeepCopyDoneByCloneMethod_thenNestedObjectShouldNotHaveSameReference() throws CloneNotSupportedException {
        String name = "Hello";
        String surname = "World";
        Balance balance = new Balance(10000, "EUR");
        BankAccountDeep bankAccount = new BankAccountDeep(NAME, SURNAME, balance);
        BankAccountDeep deepCopy = (BankAccountDeep) bankAccount.clone();
        assertNotEquals(bankAccount.getBalance(), deepCopy.getBalance());
    }

    @Test
    void whenIsADeepCopyDoneByCloneMethod_thenCopyValueShouldNotChange() throws CloneNotSupportedException {

        Balance balance = new Balance(10000, "EUR");
        BankAccountDeep bankAccount = new BankAccountDeep(NAME, SURNAME, balance);
        BankAccountDeep deepCopy = (BankAccountDeep) bankAccount.clone();
        balance.setAmount(0);
        assertNotEquals(balance.getAmount(), deepCopy.getBalance()
          .getAmount());
    }
}
