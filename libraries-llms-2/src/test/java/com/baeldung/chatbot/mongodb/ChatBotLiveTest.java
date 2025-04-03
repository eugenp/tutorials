package com.baeldung.chatbot.mongodb;

import com.baeldung.chatbot.mongodb.configuration.ChatBotConfiguration;
import com.baeldung.chatbot.mongodb.controllers.ChatBotController;
import com.baeldung.chatbot.mongodb.repositories.ArticlesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureMockMvc
@SpringBootTest(classes = {ChatBotConfiguration.class, ArticlesRepository.class, ChatBotController.class})
class ChatBotLiveTest {

    Logger log = LoggerFactory.getLogger(ChatBotLiveTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenChatBotApi_whenCallingGetEndpointWithQuestion_thenExpectedAnswersIsPresent() throws Exception {
        String chatResponse = mockMvc
          .perform(get("/chat-bot")
            .param("question", "Steps to implement Spring boot app and MongoDB"))
          .andReturn()
          .getResponse()
          .getContentAsString();

        log.info(chatResponse);
        Assertions.assertTrue(chatResponse.contains("Step 1"));
    }
}