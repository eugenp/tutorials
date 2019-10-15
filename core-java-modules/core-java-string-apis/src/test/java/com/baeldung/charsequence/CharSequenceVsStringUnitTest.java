package com.baeldung.charsequence;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class CharSequenceVsStringUnitTest {

    @Test
    public void givenUsingString_whenInstantiatingString_thenWrong() {
        CharSequence firstString = "bealdung";
        String secondString = "baeldung";

        assertNotEquals(firstString, secondString);
    }

    @Test
    public void givenIdenticalCharSequences_whenCastToString_thenEqual() {
        CharSequence charSequence1 = "baeldung_1";
        CharSequence charSequence2 = "baeldung_2";

        assertTrue(charSequence1.toString().compareTo(charSequence2.toString()) < 0);
    }

    @Test
    public void givenString_whenAppended_thenUnmodified() {
        String test = "a";
        int firstAddressOfTest = System.identityHashCode(test);
        test += "b";
        int secondAddressOfTest = System.identityHashCode(test);

        assertNotEquals(firstAddressOfTest, secondAddressOfTest);
    }

    @Test
    public void givenStringBuilder_whenAppended_thenModified() {
        StringBuilder test = new StringBuilder();
        test.append("a");
        int firstAddressOfTest = System.identityHashCode(test);
        test.append("b");
        int secondAddressOfTest = System.identityHashCode(test);

        assertEquals(firstAddressOfTest, secondAddressOfTest);
    }
}
