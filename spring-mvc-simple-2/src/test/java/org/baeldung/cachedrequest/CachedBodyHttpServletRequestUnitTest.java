package org.baeldung.cachedrequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.util.StreamUtils;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class CachedBodyHttpServletRequestUnitTest extends TestCase {

    private CachedBodyServletInputStream servletInputStream;

    @After
    public void cleanUp() throws IOException {
        if (null != servletInputStream) {
            servletInputStream.close();
        }
    }

    @Test
    public void testGivenHttpServletRequestWithBody_whenCalledGetInputStream_ThenGetsServletInputStreamWithSameBody() throws IOException {
        // Given
        byte[] cachedBody = "{\"firstName\" :\"abc\",\"lastName\" : \"xyz\",\"age\" : 30\"}".getBytes();
        MockHttpServletRequest mockeddHttpServletRequest = new MockHttpServletRequest();
        mockeddHttpServletRequest.setContent(cachedBody);
        CachedBodyHttpServletRequest request = new CachedBodyHttpServletRequest(mockeddHttpServletRequest);

        // when
        InputStream inputStream = request.getInputStream();

        // then
        assertEquals(new String(cachedBody), new String(StreamUtils.copyToByteArray(inputStream)));
    }

    @Test
    public void testGivenHttpServletRequestWithBody_whenCalledGetReader_ThenGetBufferedReaderWithSameBody() throws IOException {
        // Given
        byte[] cachedBody = "{\"firstName\" :\"abc\",\"lastName\" : \"xyz\",\"age\" : 30\"}".getBytes();
        MockHttpServletRequest mockeddHttpServletRequest = new MockHttpServletRequest();
        mockeddHttpServletRequest.setContent(cachedBody);
        CachedBodyHttpServletRequest request = new CachedBodyHttpServletRequest(mockeddHttpServletRequest);

        // when
        BufferedReader bufferedReader = request.getReader();

        // then
        String line = "";
        StringBuilder builder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line);
        }
        assertEquals(new String(cachedBody), builder.toString());
    }
}
