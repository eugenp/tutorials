package com.baeldung.apachecamel;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.*;

@Tag("live")
class ChatAppLiveTest {

    @Test
    @Timeout(30)
    void givenOpenAIAccess_whenChatting_thenExpectedResponseReceived() throws Exception {
        // Send the prompt to the model
        String response = ChatApp.runChat("Give me information about Baeldung.");

        // Validate the basic health of the response
        assertNotNull(response);
        assertFalse(response.isBlank());
        assertNotEquals("OpenAI request failed.", response);

        // Validate the response
        assertTrue(response.toLowerCase()
            .contains("baeldung"));
    }
}
