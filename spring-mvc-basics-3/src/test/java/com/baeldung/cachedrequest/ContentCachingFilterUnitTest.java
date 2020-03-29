package com.baeldung.cachedrequest;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class ContentCachingFilterUnitTest extends TestCase {

    @InjectMocks
    private ContentCachingFilter filterToTest;

    @Test
    public void testGivenHttpRequest_WhenDoFilter_thenCreatesRequestWrapperObject() throws IOException, ServletException {
        // Given
        MockHttpServletRequest mockedRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockedResponse = new MockHttpServletResponse();
        FilterChain mockedFilterChain = Mockito.mock(FilterChain.class);

        // when
        filterToTest.doFilter(mockedRequest, mockedResponse, mockedFilterChain);

        // then
        Mockito.verify(mockedFilterChain, Mockito.times(1))
            .doFilter(Mockito.any(CachedBodyHttpServletRequest.class), Mockito.any(MockHttpServletResponse.class));
    }

}
