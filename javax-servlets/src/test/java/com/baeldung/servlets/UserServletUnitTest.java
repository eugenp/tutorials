package com.baeldung.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServletUnitTest {
    
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    
    
    @BeforeClass
    public static void setUpHttpServletRequestMockInstance() {
        request = mock(HttpServletRequest.class); 
    }
    
    @BeforeClass
    public static void setUpHttpServletResponsetMockInstance() {
        response = mock(HttpServletResponse.class); 
    }
    
    @Test
    public void givenHttpServletRequestMockInstance_whenCalledgetParameter_thenCalledAtLeastOnce() {
        request.getParameter("name");
        verify(request, atLeast(1)).getParameter("name");
    }
    
    @Test
    public void givenHttpServletRequestMockInstance_whenCalledgetParameter_thenOneAssertion() {  
        when(request.getParameter("name")).thenReturn("username");
        assertThat(request.getParameter("name")).isEqualTo("username");
    }
    
    @Test
    public void givenHttpServletResponseMockInstance_whenCalledgetContentType_thenCalledAtLeastOnce() {
        response.getContentType();
        verify(response, atLeast(1)).getContentType();
    }
    
    @Test
    public void givenHttpServletResponseMockInstance_whenCalledgetContentType_thenOneAssertion() {
        when(response.getContentType()).thenReturn("text/html");
        assertThat(response.getContentType()).isEqualTo("text/html");
    }
}