package com.baeldung.servlet.context;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class ContextListenerTest {

    /**
     * Tests the contextInitialized method of ContextListener.
     * <p>
     * This test checks if the context attribute "contextPath" is correctly set to the context path
     * obtained from the ServletContext during initialization.
     */
    @Test
    void whenContextInitialized_thenSetsContextPathAttribute() {
        // Arrange
        ServletContext mockServletContext = Mockito.mock(ServletContext.class);
        ServletContextEvent mockServletContextEvent = Mockito.mock(ServletContextEvent.class);
        when(mockServletContextEvent.getServletContext()).thenReturn(mockServletContext);
        when(mockServletContext.getContextPath()).thenReturn("/exampleContext");

        ContextListener contextListener = new ContextListener();

        // Act
        contextListener.contextInitialized(mockServletContextEvent);

        // Assert
        verify(mockServletContext).setAttribute("contextPath", "/exampleContext");
    }
}