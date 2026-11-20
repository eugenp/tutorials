package com.baeldung.apachecamel;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("live")
class ChatAppWithMemoryLiveTest {

    @Test
    @Timeout(30)
    void givenOpenAIAccess_whenMultiturnChatting_thenExpectedResponseReceived() throws Exception {
        // Arrange: Prepare structured prompts forcing the model to explicitly output target keywords
        List<String> prompts = List.of("My name is Burak and I am a Java developer. Acknowledge this context briefly.",
            "What is my name and what do I do? Mention both explicitly in your response.");

        // Send the prompts to the model
        List<String> responses = ChatAppWithMemory.runMultiTurnChat(prompts);

        // Validate the basic health of responses
        assertNotNull(responses);
        assertEquals(2, responses.size(), "Should have exactly 2 responses for 2 conversational turns");
        for (String response : responses) {
            assertNotNull(response);
            assertFalse(response.isBlank());
            assertNotEquals("OpenAI request failed.", response, "A turn failed with an internal routing exception.");
        }

        // Validate Turn 2 Memory: Did it remember the name and role from Turn 1?
        String turn2Response = responses.get(1)
            .toLowerCase();
        assertTrue(turn2Response.contains("burak"), "AI forgot the name context. Response was: " + responses.get(1));
        assertTrue(turn2Response.contains("java"), "AI forgot the professional role context. Response was: " + responses.get(1));
    }
}
