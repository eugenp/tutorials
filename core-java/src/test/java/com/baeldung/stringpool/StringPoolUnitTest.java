package com.baeldung.stringpool;

import org.junit.Assert;
import org.junit.Test;

public class StringPoolUnitTest {

    @Test
    public void whenCreatingConstantStrings_thenTheirAddressesAreEqual() {
        String constantString1 = "Baeldung";
        String constantString2 = "Baeldung";

        Assert.assertTrue(constantString1 == constantString2);
    }

    @Test
    public void whenCreatingStringsWithTheNewOperator_thenTheirAddressesAreDifferent() {
        String newString1 = new String("Baeldung");
        String newString2 = new String("Baeldung");

        Assert.assertFalse(newString1 == newString2);
    }

    @Test
    public void whenComparingConstantAndNewStrings_thenTheirAddressesAreDifferent() {
        String constantString = "Baeldung";
        String newString = new String("Baeldung");

        Assert.assertFalse(constantString == newString);
    }

    @Test
    public void whenInterningAStringWithIdenticalValueToAnother_thenTheirAddressesAreEqual() {
        String constantString = "interned Baeldung";
        String newString = new String("interned Baeldung");

        Assert.assertFalse(constantString == newString);

        String internedString = newString.intern();

        Assert.assertTrue(constantString == internedString);
    }
}
