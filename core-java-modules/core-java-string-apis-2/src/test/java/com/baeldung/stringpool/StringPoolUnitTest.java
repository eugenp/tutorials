package com.baeldung.stringpool;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class StringPoolUnitTest {

    @Test
    public void whenCreatingConstantStrings_thenTheirAddressesAreEqual() {
        String constantString1 = "Baeldung";
        String constantString2 = "Baeldung";

        assertThat(constantString1).isSameAs(constantString2);
    }

    @Test
    public void whenCreatingStringsWithTheNewOperator_thenTheirAddressesAreDifferent() {
        String newString1 = new String("Baeldung");
        String newString2 = new String("Baeldung");

        assertThat(newString1).isNotSameAs(newString2);
    }

    @Test
    public void whenComparingConstantAndNewStrings_thenTheirAddressesAreDifferent() {
        String constantString = "Baeldung";
        String newString = new String("Baeldung");

        assertThat(constantString).isNotSameAs(newString);
    }

    @Test
    public void whenInterningAStringWithIdenticalValueToAnother_thenTheirAddressesAreEqual() {
        String constantString = "interned Baeldung";
        String newString = new String("interned Baeldung");

        assertThat(constantString).isNotSameAs(newString);

        String internedString = newString.intern();

        assertThat(constantString).isSameAs(internedString);
    }
}
