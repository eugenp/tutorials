package com.baeldung.springai.ollamachatbot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.springai.ollamachatbot.model.HelpDeskRequest;
import com.baeldung.springai.ollamachatbot.model.HistoryEntry;
import com.baeldung.springai.ollamachatbot.service.HelpDeskChatbotAgentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
class HelpDeskControllerLiveTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OllamaChatModel ollamaChatClient;

    @Autowired
    private HelpDeskChatbotAgentService helpDeskChatbotAgentService;

    @Autowired
    final ObjectMapper mapper = new ObjectMapper();

    @Test
    void givenPostChatRequest_whenRequested_thenReturn200() throws Exception {
        final String jsonContent = mapper.writeValueAsString(new HelpDeskRequest("Test prompt", "123"));

        mockMvc.perform(post("/helpdesk/chat").content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void givenPostChatRequests_whenRequestedWithSameHistoryId_thenSaveConversationProperly() throws Exception {
        final String firstRequest = mapper.writeValueAsString(new HelpDeskRequest("Can you help me with my internet?", "123"));

        mockMvc.perform(post("/helpdesk/chat").content(firstRequest)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        final String secondRequest = mapper.writeValueAsString(new HelpDeskRequest("My connection still don't work", "123"));

        mockMvc.perform(post("/helpdesk/chat").content(secondRequest)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        final List<HistoryEntry> currentConversationalHistory = helpDeskChatbotAgentService.getConversationalHistoryById("123");

        assertNotNull(currentConversationalHistory);
        assertEquals(2, currentConversationalHistory.size());

        HistoryEntry previousEntry = currentConversationalHistory.get(0);
        HistoryEntry currentEntry = currentConversationalHistory.get(1);

        assertTrue(previousEntry.getPrompt()
            .contains("Can you help me with my internet?"));
        assertTrue(currentEntry.getPrompt()
            .contains("My connection still don't work"));
    }
}