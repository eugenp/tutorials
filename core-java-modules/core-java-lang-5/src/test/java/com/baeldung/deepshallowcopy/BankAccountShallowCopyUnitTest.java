package com.baeldung.deepshallowcopy;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class BankAccountShallowCopyUnitTest {

    @Test
    public void givenShallowCopy_whenModifyingCopy_thenOriginalIsAlsoModified() throws CloneNotSupportedException {
        List<String> transactions = new ArrayList<>(Arrays.asList("T1", "T2")); // Use ArrayList
        BankAccountShallowCopy original = new BankAccountShallowCopy("123456", transactions);
        BankAccountShallowCopy shallowCopy = (BankAccountShallowCopy) original.clone();

        shallowCopy.getTransactions().add("T3");

        assertEquals(3, original.getTransactions().size()); // Original is modified
        assertSame(original.getTransactions(), shallowCopy.getTransactions()); // Same reference
    }
}

