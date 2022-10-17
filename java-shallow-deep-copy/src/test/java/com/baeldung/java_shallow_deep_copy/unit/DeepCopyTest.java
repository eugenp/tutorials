package com.baeldung.java_shallow_deep_copy.unit;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import com.baeldung.java_shallow_deep_copy.data.Balance;
import com.baeldung.java_shallow_deep_copy.data.BankAccountDeep;

public class DeepCopyTest {



    @Test
    void whenIsADeepCopyDoneByCopyConstructor_thenNestedObjectsAreNotTheSame() {
        Balance balance = new Balance(10000, "EUR");
        BankAccountDeep bankAccount = new BankAccountDeep("Hello", "World", balance);
        BankAccountDeep deepCopy = new BankAccountDeep(bankAccount);
        assertNotEquals(bankAccount.getBalance(), deepCopy.getBalance());
    }

    @Test
    void whenIsADeepCopyDoneByCopyConstructor_thenCopyShouldNotChange() {
        Balance balance = new Balance(10000, "EUR");
        BankAccountDeep bankAccount = new BankAccountDeep("Hello", "World", balance);
        BankAccountDeep deepCopy = new BankAccountDeep(bankAccount);
        bankAccount.getBalance().setAmount(0);
        float deepCopyAmount = deepCopy.getBalance().getAmount();
        assertNotEquals(0, deepCopyAmount);
    }

    @Test
    void whenIsADeepCopyDoneByCloneMethod_thenNestedObjectsAreNotTheSame() throws CloneNotSupportedException {
        Balance balance = new Balance(10000, "EUR");
        BankAccountDeep bankAccount = new BankAccountDeep("Hello", "World", balance);
        BankAccountDeep deepCopy = (BankAccountDeep) bankAccount.clone();
        assertNotEquals(bankAccount.getBalance(), deepCopy.getBalance());
    }

    @Test
    void whenIsADeepCopyDoneByCloneMethod_thenCopyValueShouldNotChange() throws CloneNotSupportedException {

        Balance balance = new Balance(10000, "EUR");
        BankAccountDeep bankAccount = new BankAccountDeep("Hello", "World", balance);
        BankAccountDeep deepCopy = (BankAccountDeep) bankAccount.clone();
        bankAccount.getBalance().setAmount(0);
        float deepCopyAmount = deepCopy.getBalance().getAmount();
        assertNotEquals(0, deepCopyAmount);
    }

}
