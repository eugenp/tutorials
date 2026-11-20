package com.baeldung.mcpannotations;

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

        // Simulate work and report detailed progress
        // 50% complete (0.5 out of 1.0)
        context.progress(p -> p.progress(0.5).total(1.0).message("Processing records..."));

        return "Processed " + dataId;
    }
}
