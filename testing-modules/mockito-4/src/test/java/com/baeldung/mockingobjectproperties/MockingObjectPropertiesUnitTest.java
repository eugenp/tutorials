package com.baeldung.mockingobjectproperties;

import org.junit.jupiter.api.Test;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;

public class MockingObjectPropertiesUnitTest {

    @Test
    void givenMockRequest_whenUsedWithoutStubbing_thenReturnsNull() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        mockRequest.setAttribute("search", "Mockito Example");

        MockingObjectProperties helper = new MockingObjectProperties();
        Object value = helper.getSearchAttribute(mockRequest);

        assertNull(value, "Expected null because mock does not retain state");
    }

    @Test
    void givenMockRequest_whenStubbedProperly_thenReturnsExpectedValue() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getAttribute("search"))
          .thenReturn("Mockito Example");

        MockingObjectProperties helper = new MockingObjectProperties();
        Object value = helper.getSearchAttribute(mockRequest);

        assertEquals("Mockito Example", value);
    }

    @Test
    void givenMockRequest_whenSimulatingDynamicAttributes_thenBehavesLikeRealObject() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        Map<String, Object> attributes = new HashMap<>();

        when(mockRequest.getAttribute(anyString()))
          .thenAnswer(invocation -> attributes.get(invocation.getArgument(0)));

        doAnswer(invocation -> {
            attributes.put(invocation.getArgument(0), invocation.getArgument(1));
            return null;
        }).when(mockRequest).setAttribute(anyString(), any());

        MockingObjectProperties helper = new MockingObjectProperties();

        mockRequest.setAttribute("search", "Mockito Example");
        Object value = helper.getSearchAttribute(mockRequest);

        assertEquals("Mockito Example", value);
    }

    @Test
    void givenMockRequest_whenVerifyingInteractions_thenWorksAsExpected() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);

        mockRequest.setAttribute("search", "Mockito Example");
        mockRequest.getAttribute("search");

        verify(mockRequest)
          .setAttribute("search", "Mockito Example");
        verify(mockRequest)
          .getAttribute("search");
        verify(mockRequest, never())
          .getAttribute("nonexistent");
    }
}
