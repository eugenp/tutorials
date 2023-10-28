package com.baeldung.charsequence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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

    @Test
    public void givenCharSequenceAsString_whenConvertingUsingCasting_thenCorrect() {
        String expected = "baeldung";
        CharSequence charSequence = "baeldung";
        String explicitCastedString = (String) charSequence;

        assertEquals(expected, charSequence);
        assertEquals(expected, explicitCastedString);
    }

    @Test(expected = ClassCastException.class)
    public void givenCharSequenceAsStringBuiler_whenConvertingUsingCasting_thenThrowException() {
        CharSequence charSequence = new StringBuilder("baeldung");
        String castedString = (String) charSequence;
    }

    @Test
    public void givenCharSequence_whenConvertingUsingToString_thenCorrect() {
        String expected = "baeldung";
        CharSequence charSequence1 = "baeldung";
        CharSequence charSequence2 = new StringBuilder("baeldung");

        assertEquals(expected, charSequence1.toString());
        assertEquals(expected, charSequence2.toString());
    }

    @Test
    public void givenCharSequence_whenConvertingUsingValueOf_thenCorrect() {
        String expected = "baeldung";
        CharSequence charSequence1 = "baeldung";
        CharSequence charSequence2 = new StringBuilder("baeldung");
        CharSequence charSequence3 = null;

        assertEquals(expected, String.valueOf(charSequence1));
        assertEquals(expected, String.valueOf(charSequence2));
        assertEquals("null", String.valueOf(charSequence3));
    }

}
