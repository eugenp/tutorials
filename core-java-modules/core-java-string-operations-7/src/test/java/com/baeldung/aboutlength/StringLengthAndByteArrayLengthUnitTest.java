package com.baeldung.aboutlength;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;

public class StringLengthAndByteArrayLengthUnitTest {

    @Test
    void whenStrWithAllAsciiChar_thenStrLengthAndBytesLengthAreEqual() {
        String s = "beautiful";
        assertEquals(9, s.length());
        assertEquals(9, s.getBytes().length);
    }

    @Test
    void whenStrWithUnicodeChar_thenStrLengthAndBytesLengthAreNotEqual() {
        assertEquals("f6", Integer.toHexString('ö'));
        assertEquals("7f8e", Integer.toHexString('美'));
        assertEquals("4e3d", Integer.toHexString('丽'));

        String de = "schöne";
        assertEquals(6, de.length());
        assertEquals(7, de.getBytes().length);

        String cn = "美丽";
        assertEquals(2, cn.length());
        assertEquals(6, cn.getBytes().length);
    }

    @Test
    void whenUsingUTF_32_thenBytesLengthIs4TimesStrLength() {
        Charset UTF_32 = Charset.forName("UTF_32");

        String en = "beautiful";
        assertEquals(9, en.length());
        assertEquals(9 * 4, en.getBytes(UTF_32).length);

        String de = "schöne";
        assertEquals(6, de.length());
        assertEquals(6 * 4, de.getBytes(UTF_32).length);

        String cn = "美丽";
        assertEquals(2, cn.length());
        assertEquals(2 * 4, cn.getBytes(UTF_32).length);
    }
}