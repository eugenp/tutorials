package com.baeldung.mcpannotations;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springaicommunity.mcp.context.McpSyncRequestContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @McpTool(description = "Search for customers using structured criteria")
    public List<String> searchCustomers(
            @McpToolParam(description = "The search filters") CustomerSearchCriteria criteria
    ) {
        // In a real app, this would query a database
        return List.of("Customer A", "Customer B");
    }

    @McpTool(name = "long_running_process")
    public String processData(
            String dataId,
            McpSyncRequestContext context
    ) {
        context.info("Starting processing for ID: " + dataId);

        // Simulate work
        context.progress(50);

        return "Processed " + dataId;
    }
}
