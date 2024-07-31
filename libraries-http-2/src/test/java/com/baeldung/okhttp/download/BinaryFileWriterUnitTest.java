package com.baeldung.okhttp.download;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BinaryFileWriterUnitTest {

    @Mock
    private OutputStream outputStream;

    @Test
    public void givenInputStream_whenWrite_thenExpectWritten() throws Exception {
        InputStream inputStream = mock(InputStream.class);
        when(inputStream.read(any(), anyInt(), anyInt())).thenReturn(10, -1);

        try (BinaryFileWriter tested = new BinaryFileWriter(outputStream, progress -> assertEquals(100.0, progress, .0))) {
            long result = tested.write(inputStream, 10);

            assertEquals(10, result);
            verify(outputStream).write(any(), eq(0), eq(10));
            verify(inputStream).close();
        }
        verify(outputStream).close();
    }

    @Test
    public void givenInputStreamEmpty_whenWrite_thenExpectNotWritten() throws Exception {
        InputStream inputStream = mock(InputStream.class);

        try (BinaryFileWriter tested = new BinaryFileWriter(outputStream, progress -> assertEquals(100.0, progress, .0))) {
            long result = tested.write(inputStream, 1);

            assertEquals(0, result);
            verify(outputStream, times(0)).write(any(), anyInt(), anyInt());
            verify(inputStream).close();
        }
        verify(outputStream).close();
    }

}