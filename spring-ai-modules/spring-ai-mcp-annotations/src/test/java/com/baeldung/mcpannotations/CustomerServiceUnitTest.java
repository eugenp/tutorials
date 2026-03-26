package com.baeldung.mcpannotations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springaicommunity.mcp.context.McpSyncRequestContext;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerServiceUnitTest {

    private final CustomerService customerService = new CustomerService();

    @Mock
    private McpSyncRequestContext mockContext;

    @Test
    void givenSearchCriteria_whenSearchCustomers_thenReturnsMatchingCustomers() {
        // Given
        CustomerSearchCriteria criteria = new CustomerSearchCriteria("EU", true, 10);

        // When
        List<String> results = customerService.searchCustomers(criteria);

        // Then
        assertEquals(2, results.size());
        assertTrue(results.contains("Customer A"));
    }

    @Test
    void givenDataId_whenProcessData_thenLogsInfoAndUpdatesProgress() {
        // Given
        String dataId = "12345";

        // When
        String result = customerService.processData(dataId, mockContext);

        // Then
        assertEquals("Processed 12345", result);

        // Verify the logger was called
        verify(mockContext).info("Starting processing for ID: 12345");

        // Verify progress was called with ANY configuration lambda
        verify(mockContext).progress(any(Consumer.class));
    }
}