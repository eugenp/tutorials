package com.baeldung.mcpannotations;

import io.modelcontextprotocol.spec.McpSchema;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SystemLogServiceUnitTest {

    private final SystemLogService systemLogService = new SystemLogService();

    @Test
    void givenServiceNameAndDate_whenReadLog_thenReturnsReadableMessage() {
        // Given
        String service = "payment-service";
        String date = "2023-10-01";

        // When
        String log = systemLogService.readLog(service, date);

        // Then
        assertTrue(log.contains(service));
        assertTrue(log.contains(date));
    }

    @Test
    void givenDiagramId_whenGetDiagram_thenReturnsBinaryPngImage() {
        // Given
        String diagramId = "arch-01";

        // When
        McpSchema.ReadResourceResult result = systemLogService.getDiagram(diagramId);

        // Then
        assertNotNull(result);
        assertFalse(result.contents().isEmpty());

        McpSchema.ResourceContents content = result.contents().get(0);
        assertEquals("diagrams://" + diagramId, content.uri());
        assertEquals("image/png", content.mimeType());
        assertInstanceOf(McpSchema.BlobResourceContents.class, content);
    }
}