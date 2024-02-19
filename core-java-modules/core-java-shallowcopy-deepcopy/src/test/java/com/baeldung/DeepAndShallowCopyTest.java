package com.baeldung;

import com.baeldung.model.BankPojoForDeepCopy;
import com.baeldung.model.BankPojoForShallowCopy;
import com.baeldung.model.BranchPojoForDeepCopy;
import com.baeldung.model.BranchPojoForShallowCopy;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DeepAndShallowCopyTest {

    @Test
    public void testShallowCopy() throws CloneNotSupportedException {
        BankPojoForShallowCopy originalBank = new BankPojoForShallowCopy();
        BranchPojoForShallowCopy originalBranch = new BranchPojoForShallowCopy();
        originalBank.setName("Bank1");
        originalBank.setCode(1);
        originalBranch.setCity("City1");
        originalBank.setBranch(originalBranch);

        BankPojoForShallowCopy copiedBank = (BankPojoForShallowCopy) originalBank.clone();

        copiedBank.setName("Bank2");
        copiedBank.getBranch()
            .setCity("City2");

        assertNotEquals(originalBank.getName(), copiedBank.getName());
        // We changed name of the city for copied bank but original bank is still pointing to the same reference
        assertEquals(originalBank.getBranch()
            .getCity(), copiedBank.getBranch()
            .getCity());
    }

    @Test
    public void testDeepCopy() throws CloneNotSupportedException {
        BankPojoForDeepCopy originalBank = new BankPojoForDeepCopy();
        BranchPojoForDeepCopy originalBranch = new BranchPojoForDeepCopy();
        originalBank.setName("Bank1");
        originalBank.setCode(1);
        originalBranch.setCity("City1");
        originalBank.setBranch(originalBranch);

        BankPojoForDeepCopy copiedBank = (BankPojoForDeepCopy) originalBank.clone();

        copiedBank.setName("Bank2");
        copiedBank.getBranch()
            .setCity("City2");

        assertNotEquals(originalBank.getName(), copiedBank.getName());
        // We changed name of the city for copied bank and its not reflected in original bank's branch
        assertNotEquals(originalBank.getBranch()
            .getCity(), copiedBank.getBranch()
            .getCity());
    }
}
