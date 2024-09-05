package com.baeldung.deepshallowcopy;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class BankAccountDeepCopyUnitTest {

    @Test
    public void givenDeepCopy_whenModifyingCopy_thenOriginalIsNotModified() throws CloneNotSupportedException {
        List<String> transactions = Arrays.asList("T1", "T2");
        BankAccountDeepCopy original = new BankAccountDeepCopy("123456", transactions);
        BankAccountDeepCopy deepCopy = (BankAccountDeepCopy) original.clone();

        deepCopy.getTransactions().add("T3");

        assertEquals(2, original.getTransactions().size()); // Original remains unchanged
        assertEquals(3, deepCopy.getTransactions().size()); // Deep copy is modified independently
        assertNotSame(original.getTransactions(), deepCopy.getTransactions()); // Different references
    }
}
