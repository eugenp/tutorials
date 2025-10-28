package com.baeldung.mockingobjectproperties;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;

public class MockingObjectPropertiesUnitTest {

    @Test
    void givenMockRequest_whenSimulatingDynamicAttributes_thenBehavesLikeRealObject() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        Map<String, Object> attributes = new HashMap<>();

        // Simulate internal state for getAttribute()
        when(mockRequest.getAttribute(anyString()))
                .thenAnswer(invocation -> attributes.get(invocation.getArgument(0)));

        // Intercept setAttribute() calls to store values
        doAnswer(invocation -> {
            attributes.put(invocation.getArgument(0), invocation.getArgument(1));
            return null;
        }).when(mockRequest).setAttribute(anyString(), any());

        // Use the main class
        MockingObjectProperties helper = new MockingObjectProperties();

        // Set and get attribute
        mockRequest.setAttribute("search", "Mockito Example");
        Object value = helper.getSearchAttribute(mockRequest);

        // Assert the value is correctly retrieved
        assertEquals("Mockito Example", value);

        // Verify interactions
        verify(mockRequest).setAttribute("search", "Mockito Example");
        verify(mockRequest).getAttribute("search");
        verify(mockRequest, never()).getAttribute("nonexistent");
    }
}
