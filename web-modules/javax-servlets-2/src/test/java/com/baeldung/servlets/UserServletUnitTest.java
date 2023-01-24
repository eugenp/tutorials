package com.baeldung.servlets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import mockit.Expectations;
import mockit.Mocked;

class UserServletUnitTest {

    private UserServlet servlet;
    private StringWriter writer;
    
    @Mocked
    HttpServletRequest mockRequest;
    @Mocked
    HttpServletResponse mockResponse;

    @BeforeEach
    public void setUp() throws IOException {
        servlet = new UserServlet();
        writer = new StringWriter();
    }

    @Test
    void givenHttpServletRequest_whenUsingAnonymousClass_thenReturnsParameterValues() throws IOException {
        final Map<String, String[]> params = new HashMap<>();
        params.put("firstName", new String[] { "Anonymous Class" });
        params.put("lastName", new String[] { "Test" });

        servlet.doGet(TestUtil.getRequest(params), TestUtil.getResponse(writer));

        assertThat(writer.toString()).isEqualTo("Full Name: Anonymous Class Test");
    }

    @Test
    void givenHttpServletRequest_whenMockedWithMockito_thenReturnsParameterValues() throws IOException {
        // mock HttpServletRequest & HttpServletResponse
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // mock the returned value of request.getParameterMap()
        when(request.getParameter("firstName")).thenReturn("Mockito");
        when(request.getParameter("lastName")).thenReturn("Test");
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        servlet.doGet(request, response);

        assertThat(writer.toString()).isEqualTo("Full Name: Mockito Test");
    }

    @Test
    void givenHttpServletRequest_whenMockedWithJMockit_thenReturnsParameterValues() throws IOException {
        
        new Expectations() {{
            mockRequest.getParameter("firstName"); result = "JMockit";
            mockRequest.getParameter("lastName"); result = "Test";
            mockResponse.getWriter(); result = new PrintWriter(writer);
         }};
        
        servlet.doGet(mockRequest, mockResponse);
        
        assertThat(writer.toString()).isEqualTo("Full Name: JMockit Test");
    }

    @Test
    void givenHttpServletRequest_whenUsingMockHttpServletRequest_thenReturnsParameterValues() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("firstName", "Spring");
        request.setParameter("lastName", "Test");
        MockHttpServletResponse response = new MockHttpServletResponse();

        servlet.doGet(request, response);

        assertThat(response.getContentAsString()).isEqualTo("Full Name: Spring Test");
    }

}
