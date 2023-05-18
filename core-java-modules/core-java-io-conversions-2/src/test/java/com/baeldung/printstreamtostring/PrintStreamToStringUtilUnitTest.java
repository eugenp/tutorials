package com.baeldung.printstreamtostring;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class PrintStreamToStringUtilUnitTest {

    @Test
    public void whenUsingByteArrayOutputStreamClass_thenConvert() throws IOException {
        assertEquals("test", PrintStreamToStringUtil.usingByteArrayOutputStreamClass("test"));
        assertEquals("", PrintStreamToStringUtil.usingByteArrayOutputStreamClass(""));
        assertNull(PrintStreamToStringUtil.usingByteArrayOutputStreamClass(null));
    }

    @Test
    public void whenCustomOutputStream_thenConvert() throws IOException {
        assertEquals("world", PrintStreamToStringUtil.usingCustomOutputStream("world"));
        assertEquals("", PrintStreamToStringUtil.usingCustomOutputStream(""));
        assertNull(PrintStreamToStringUtil.usingCustomOutputStream(null));
    }

    @Test
    public void whenUsingApacheCommonsIO_thenConvert() {
        assertEquals("hello", PrintStreamToStringUtil.usingApacheCommonsIO("hello"));
        assertEquals("", PrintStreamToStringUtil.usingApacheCommonsIO(""));
        assertNull(PrintStreamToStringUtil.usingApacheCommonsIO(null));
    }
}
