package org.baeldung.guava;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;

import org.junit.Test;

import com.google.common.io.CountingOutputStream;

public class GuavaCountingOutputStreamTest {

    @Test
    public void givenData_whenWrittenToStream_thenGetCorrectCount() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CountingOutputStream cos = new CountingOutputStream(out);
        
        byte[] data = new byte[10];
        
        cos.write(data);
        
        assertEquals(10, cos.getCount());
        
    }
}
