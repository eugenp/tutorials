package com.baeldung.mcpannotations;

import io.modelcontextprotocol.spec.McpSchema;
import org.springaicommunity.mcp.annotation.McpResource;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemLogService {

    @McpResource(
            uri = "logs://{serviceName}/{date}",
            name = "System Logs",
            description = "Read logs for a specific service and date"
    )
    public String readLog(
            @McpToolParam(description = "Service Name") String serviceName,
            @McpToolParam(description = "Date YYYY-MM-DD") String date
    ) {
        return "Logs for " + serviceName + " on " + date + ": No errors found.";
    }

    @McpResource(uri = "diagrams://{id}", name = "System Architecture Diagram")
    public McpSchema.ReadResourceResult getDiagram(String id) {
        String base64Image = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNk+A8AAQUBAScY42YAAAAASUVORK5CYII=";

        return new McpSchema.ReadResourceResult(List.of(
                new McpSchema.BlobResourceContents(
                        "diagrams://" + id,
                        "image/png",
                        base64Image
                )
        ));
    }
}
