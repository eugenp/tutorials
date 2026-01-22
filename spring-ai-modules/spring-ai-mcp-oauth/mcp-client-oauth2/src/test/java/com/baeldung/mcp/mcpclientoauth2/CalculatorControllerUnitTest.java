package com.baeldung.mcp.mcpclientoauth2;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class CalculatorControllerUnitTest {

    private MockMvc mockMvc;
    private ChatClient chatClient;

    @BeforeEach
    void setUp() {
        chatClient = mock(ChatClient.class, Mockito.RETURNS_DEEP_STUBS);
        CalculatorController controller = new CalculatorController(chatClient);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .build();
    }

    @Test
    void givenValidExpression_whenCalculateEndpointCalled_thenReturnsExpectedResult() throws Exception {
        when(chatClient.prompt()
            .user(anyString())
            .call()
            .content()).thenReturn("42");
        mockMvc.perform(MockMvcRequestBuilders.get("/calculate")
                .param("expression", "40 + 2"))
            .andExpect(MockMvcResultMatchers.status()
                .isOk())
            .andExpect(MockMvcResultMatchers.content()
                .string("42"));
    }

    @Test
    void givenHomeRequest_whenHomeEndpointCalled_thenReturnsHtmlWithTitle() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(MockMvcResultMatchers.status()
                .isOk())
            .andExpect(MockMvcResultMatchers.content()
                .string(org.hamcrest.Matchers.containsString("MCP Calculator with OAuth2")));
    }
}
