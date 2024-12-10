package com.baeldung.servlet.context;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mockit.Mocked;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockServletContext;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ServletRequestTest {

    @Mocked
    HttpServletRequest request;
    @Mocked
    HttpServletResponse response;
    private ServletRequest servletRequest;
    private StringWriter stringWriter;

    @BeforeEach
    void setUp() {
        servletRequest = new ServletRequest();
        stringWriter = new StringWriter();
    }

    @Test
    void givenHttpServletContext_thenReturnsServletContextParameters() throws IOException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        String message = "Welcome to baeldung!";
        MockServletContext mockContext = new MockServletContext();
        mockContext.addInitParameter("message", message);

        when(request.getServletContext()).thenReturn(mockContext);

        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servletRequest.doGet(request, response);

        assertEquals(message, stringWriter.toString());
    }

    @Test
    void givenHttpServletContext_whenMocked_thenReturnsServletContextParameters() throws IOException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        String message = "Welcome to baeldung!";
        MockServletContext mockContext = new MockServletContext();
        mockContext.addInitParameter("message", message);

        when(request.getServletContext()).thenReturn(mockContext);

        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        servletRequest.doGet(request, response);

        assertEquals(message, stringWriter.toString());
    }

    @Test
    void whenNoInitParameter_thenReturnsEmptyResponse() throws IOException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        MockServletContext mockContext = new MockServletContext();
        // No parameter is added here

        when(request.getServletContext()).thenReturn(mockContext);
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servletRequest.doGet(request, response);

        assertEquals("null", stringWriter.toString());
    }

    @Test
    void whenInitParameterIsNull_thenReturnsEmptyResponse() throws IOException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        MockServletContext mockContext = new MockServletContext();
        mockContext.addInitParameter("message", null); // Explicitly setting parameter to null

        when(request.getServletContext()).thenReturn(mockContext);
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servletRequest.doGet(request, response);

        assertEquals("null", stringWriter.toString());
    }
}