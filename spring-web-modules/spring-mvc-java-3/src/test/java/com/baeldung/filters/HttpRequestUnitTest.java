package com.baeldung.filters;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class HttpRequestUnitTest {

    @Test
    public void givenHttpServletRequest_whenCalling_getReaderAfter_getInputStream_thenThrowIllegalStateException() throws IOException {
        HttpServletRequest request = new MockHttpServletRequest();
        try (ServletInputStream ignored = request.getInputStream()) {
            IllegalStateException exception = assertThrows(IllegalStateException.class, request::getReader);
            assertEquals("Cannot call getReader() after getInputStream() has already been called for the current request",
                    exception.getMessage());
        }
    }

    @Test
    public void givenServletRequest_whenDoFilter_thenCanCallBoth() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        Filter filter = new CacheRequestContentFilter();
        filter.doFilter(req, res, chain);

        ServletRequest request = chain.getRequest();
        assertTrue(request instanceof ContentCachingRequestWrapper);

        // now we can call both getInputStream() and getReader()
        request.getInputStream();
        request.getReader();
    }

}
