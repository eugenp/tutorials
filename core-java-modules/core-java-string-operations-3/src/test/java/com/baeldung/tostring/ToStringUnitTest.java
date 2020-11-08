package com.baeldung.tostring;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

public class ToStringUnitTest {
    @Test
    public void givenString_whenCastToObjectAndString_thenSameAndNoException() {
        String input = "baeldung";

        Object obj = input;
        String str = (String) obj;

        assertEquals(obj, str);
        assertEquals(str, input);
        assertSame(input, str);
    }

    @Test(expected = ClassCastException.class)
    public void givenIntegerObject_whenCastToObjectAndString_thenCastClassException() {
        Integer input = 1234;

        Object obj = input;
        String str = (String) obj;
    }

    @Test
    public void givenNullInteger_whenCastToObjectAndString_thenSameAndNoException() {
        Integer input = null;

        Object obj = input;
        String str = (String) obj;

        assertEquals(obj, str);
        assertEquals(str, input);
        assertSame(input, str);
    }

    @Test(expected = NullPointerException.class)
    public void givenNullInteger_whenToString_thenNullPointerException() {
        Integer input = null;

        String str = input.toString();
    }

    @Test
    public void givenInteger_whenCastToObject_thenToStringEquals() {
        Integer input = 1234;

        Object obj = input;

        assertEquals("1234", input.toString());
        assertEquals("1234", obj.toString());
        assertEquals(input.toString(), obj.toString());
    }

    @Test
    public void givenString_whenToString_thenSame() {
        String str = "baeldung";

        assertEquals("baeldung", str.toString());
        assertSame(str, str.toString());
    }

    @Test
    public void givenString_whenCastToObject_thenCastToStringReturnsSame() {
        String input = "baeldung";

        Object obj = input;

        assertSame(input, StringCastUtils.castToString(obj));
    }

    @Test
    public void givenInteger_whenCastToObject_thenCastToStringReturnsNull() {
        Integer input = 1234;

        Object obj = input;

        assertEquals(null, StringCastUtils.castToString(obj));
    }

    @Test
    public void givenIntegerNull_whenCastToObject_thenCastToStringReturnsNull() {
        Integer input = null;

        Object obj = input;

        assertEquals(null, StringCastUtils.castToString(obj));
    }

    @Test
    public void givenIntegerNotNull_whenCastToObject_thenGetToStringReturnsString() {
        Integer input = 1234;

        Object obj = input;

        assertEquals("1234", StringCastUtils.getStringRepresentation(obj));
        assertNotSame("1234", StringCastUtils.getStringRepresentation(obj));
    }
}
