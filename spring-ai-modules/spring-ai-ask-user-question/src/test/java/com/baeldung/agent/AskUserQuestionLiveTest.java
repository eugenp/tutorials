package com.baeldung.agent;

import java.io.IOException;
import java.net.URI;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springaicommunity.agent.tools.AskUserQuestionTool;
import org.springaicommunity.agent.tools.AskUserQuestionTool.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Live verification against a running Ollama server: the real model must invoke
 * AskUserQuestionTool.
 * Asserts only that the tool was invoked with well-formed questions, never the
 * model's wording.
 */
@SpringBootTest
class AskUserQuestionLiveTest {

    private static final int MAX_ATTEMPTS = 3;

    /** The real runner blocks on System.in during context startup - neutralize it. */
    @MockitoBean
    ChatLoop chatLoop;

    @Autowired
    ChatModel chatModel;

    @Value("${spring.ai.ollama.base-url}")
    String ollamaBaseUrl;

    @Test
    void givenOllamaReachable_whenPlanningAnEvent_thenModelInvokesAskUserQuestionTool() {
        assumeTrue(ollamaReachable(),
            "Ollama is not reachable at " + ollamaBaseUrl + " - skipping live test");

        // One batch per tool invocation: the model may ask several rounds of
        // follow-up questions inside a single call, and the 1-4 contract applies
        // per invocation, not per test.
        List<List<Question>> invocations = Collections.synchronizedList(new ArrayList<List<Question>>());
        AskUserQuestionTool tool = AskUserQuestionTool.builder()
            .questionHandler(questions -> {
                invocations.add(new ArrayList<Question>(questions));
                Map<String, String> answers = new HashMap<>();
                for (Question question : questions) {
                    answers.put(question.question(),
                        question.options().isEmpty() ? "" : question.options().get(0).label());
                }
                return answers;
            })
            .build();

        // Own ChatClient with a recording stub handler: deliberately NOT the
        // application's ChatClient bean, which is wired to CommandLineQuestionHandler
        // and would block on System.in inside the tool call.
        ChatClient chatClient = ChatClient.builder(chatModel)
            .defaultSystem("You are an interactive CLI assistant.\n"
                + "If you need information, a preference or disambiguation from the user,\n"
                + "always use an available tool to ask - never ask in ordinary assistant text.")
            .defaultTools(tool)
            .build();

        int attempts = 0;
        do {
            attempts++;
            chatClient.prompt()
                .user("I want to plan an event")
                .call()
                .content();
        } while (invocations.isEmpty() && attempts < MAX_ATTEMPTS);

        assertFalse(invocations.isEmpty(),
            "the model did not call AskUserQuestionTool after " + attempts + " attempt(s)");
        for (List<Question> batch : invocations) {
            // Enforced by the library itself (validateQuestions), asserted for clarity:
            assertTrue(batch.size() >= 1 && batch.size() <= 4,
                "each tool invocation must carry 1-4 questions, got " + batch.size());
            for (Question question : batch) {
                // Only what our handler and answer keying actually depend on.
                // (Question's constructor already rejects blank text/headers, and the
                // option count of 2-4 is a model-side guideline the library merely
                // warns about - not something to police in a live test.)
                assertFalse(question.question().isBlank(), "question text must not be blank");
                assertNotNull(question.options(), "options must be present");
                assertFalse(question.options().isEmpty(),
                    "the handler needs at least one option to offer");
            }
        }
    }

    private boolean ollamaReachable() {
        try {
            URLConnection connection = URI.create(ollamaBaseUrl + "/api/tags").toURL().openConnection();
            connection.setConnectTimeout(2000);
            connection.connect();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

}
