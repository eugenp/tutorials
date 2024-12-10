package com.baeldung.servlet.context;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.PrintWriter;

import static org.mockito.Mockito.*;

class GetServletContextTest {

    /**
     * Test that the `doGet` method writes the correct message
     * retrieved from the ServletContext's init parameter `message`.
     */
    @Test
    void whenDoGet_thenWritesMessageFromServletContext() throws Exception {
        // Arrange
        GetServletContext servlet = new GetServletContext();
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        ServletContext mockServletContext = mock(ServletContext.class);
        PrintWriter writer = mock(PrintWriter.class);

        when(mockResponse.getWriter()).thenReturn(writer);
        when(mockServletContext.getInitParameter("message")).thenReturn("Hello, World!");
        servlet.init(mock(ServletConfig.class));
        when(servlet.getServletContext()).thenReturn(mockServletContext);

        // Act
        servlet.doGet(mockRequest, mockResponse);

        // Assert
        verify(mockResponse.getWriter()).write("Hello, World!");
    }

    /**
     * Test that the `doGet` method handles a null message gracefully
     * when the ServletContext's init parameter `message` is not set.
     */
    @Test
    void whenDoGet_thenHandlesNullMessageGracefully() throws Exception {
        // Arrange
        GetServletContext servlet = new GetServletContext();
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        ServletContext mockServletContext = mock(ServletContext.class);
        PrintWriter writer = mock(PrintWriter.class);

        when(mockResponse.getWriter()).thenReturn(writer);
        when(mockServletContext.getInitParameter("message")).thenReturn(null);
        servlet.init(mock(ServletConfig.class));

        when(servlet.getServletContext()).thenReturn(mockServletContext);

        // Act
        servlet.doGet(mockRequest, mockResponse);

        // Assert
        verify(mockResponse.getWriter()).write((String) null);
    }

    /**
     * Test that the `doGet` method interacts with the ServletContext
     * to retrieve the `message` init parameter.
     */
    @Test
    void whenDoGet_thenInteractsWithServletContext() throws Exception {
        // Arrange
        GetServletContext servlet = new GetServletContext();
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        ServletContext mockServletContext = mock(ServletContext.class);
        PrintWriter writer = mock(PrintWriter.class);

        when(mockResponse.getWriter()).thenReturn(writer);
        when(mockServletContext.getInitParameter("message")).thenReturn("Test Message");
        servlet.init(mock(ServletConfig.class));
        when(servlet.getServletContext()).thenReturn(mockServletContext);

        // Act
        servlet.doGet(mockRequest, mockResponse);

        // Assert
        verify(mockServletContext).getInitParameter("message");
    }
}