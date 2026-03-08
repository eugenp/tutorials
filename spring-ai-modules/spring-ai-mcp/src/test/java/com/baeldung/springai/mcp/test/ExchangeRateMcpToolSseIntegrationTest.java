package com.baeldung.springai.mcp.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Objects;

import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class ExchangeRateMcpToolSseIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestMcpClientFactory testMcpClientFactory;

    @MockBean
    private ExchangeRateService exchangeRateService;

    private McpSyncClient client;

    @BeforeEach
    void setUp() {
        client = testMcpClientFactory.create("http://localhost:" + port);
        client.initialize();
    }

    @AfterEach
    void cleanUp() {
        client.closeGracefully();
    }

    @Test
    void whenMcpClientListTools_thenTheToolIsRegistered() {
        boolean registered = client.listTools().tools().stream()
            .anyMatch(tool -> Objects.equals(tool.name(), "getLatestExchangeRate"));
        assertThat(registered).isTrue();
    }

    @Test
    void whenMcpClientCallTool_thenTheToolReturnsMockedResponse() {
        when(exchangeRateService.getLatestExchangeRate("GBP")).thenReturn(
            new ExchangeRateResponse(1.0, "GBP", "2026-03-08", Map.of("USD", 1.27))
        );

        McpSchema.Tool exchangeRateTool = client.listTools().tools().stream()
            .filter(tool -> "getLatestExchangeRate".equals(tool.name()))
            .findFirst()
            .orElseThrow();

        String argumentName = exchangeRateTool.inputSchema().properties().keySet().stream()
            .findFirst()
            .orElseThrow();

        McpSchema.CallToolResult result = client.callTool(
            new McpSchema.CallToolRequest("getLatestExchangeRate", Map.of(argumentName, "GBP"))
        );

        assertThat(result).isNotNull();
        assertThat(result.isError()).isFalse();
        assertTrue(result.toString().contains("GBP"));
    }
}
