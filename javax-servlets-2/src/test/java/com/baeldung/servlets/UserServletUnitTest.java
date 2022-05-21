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

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class UserServletUnitTest {

    private UserServlet servlet;
    private StringWriter writer;

    @BeforeEach
    public void setUp() throws IOException {
        servlet = new UserServlet();
        writer = new StringWriter();
    }

    @Test
    void givenHttpServletRequest_whenUsingAnonymousClass_thenReturnsParameterValues() throws IOException {
        final Map<String, String[]> params = new HashMap<>();
        params.put("firstName", new String[] { "bael" });
        params.put("lastName", new String[] { "dung" });

        servlet.doGet(TestUtil.getRequest(params), TestUtil.getResponse(writer));

        assertThat(writer.toString()).isEqualTo("Full Name: bael dung");
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
    void givenHttpServletRequest_whenMockedWithJMock_thenReturnsParameterValues() throws IOException {
        Mockery context = new Mockery();
        // mock HttpServletRequest & HttpServletResponse
        HttpServletRequest request = context.mock(HttpServletRequest.class);
        HttpServletResponse response = context.mock(HttpServletResponse.class);

        // mock the methods and return types
        context.checking(new Expectations() {
            {
                oneOf(request).getParameter("firstName");
                will(returnValue("jMock"));
                oneOf(request).getParameter("lastName");
                will(returnValue("test"));
                oneOf(response).getWriter();
                will(returnValue(new PrintWriter(writer)));
            }
        });

        servlet.doGet(request, response);

        context.assertIsSatisfied();
        assertThat(writer.toString()).isEqualTo("Full Name: jMock test");
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
