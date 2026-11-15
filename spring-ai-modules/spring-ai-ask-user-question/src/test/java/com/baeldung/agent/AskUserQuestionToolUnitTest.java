package com.baeldung.agent;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;
import org.springaicommunity.agent.tools.AskUserQuestionTool;
import org.springaicommunity.agent.tools.AskUserQuestionTool.InvalidUserAnswerException;
import org.springaicommunity.agent.tools.AskUserQuestionTool.Question;

import static org.junit.jupiter.api.Assertions.*;

/**
 * One deterministic test for the article's AskUserQuestionTool wiring: a stub
 * QuestionHandler only - no model, no Spring context, no Ollama, no System.in.
 */
class AskUserQuestionToolUnitTest {

    private static final Question EVENT_QUESTION = new Question(
        "What kind of event are you planning?", "Event Type",
        List.of(new Question.Option("Conference/Seminar", "Professional gathering with speakers."),
            new Question.Option("Social Party/Celebration", "Casual social event (birthday, gala, etc.)."),
            new Question.Option("Workshop/Training", "Interactive learning session."),
            new Question.Option("Online Event (Webinar)", "Virtual event held online.")),
        false);

    @Test
    void whenAnswersCompleteAndIncomplete_thenKeyedResultReturnedAndExceptionThrown() {
        AtomicReference<List<Question>> received = new AtomicReference<>();

        // a complete answer, keyed by question text, is accepted
        AskUserQuestionTool answered = AskUserQuestionTool.builder()
            .questionHandler(questions -> {
                received.set(questions);
                return Map.of(questions.get(0).question(), "Social Party/Celebration");
            })
            .build();

        String result = answered.askUserQuestion(List.of(EVENT_QUESTION), null);

        assertNotNull(received.get(), "the handler must receive the model's questions");
        assertEquals("What kind of event are you planning?", received.get().get(0).question());
        assertEquals(4, received.get().get(0).options().size());
        assertEquals("User has answered your questions: "
            + "{\"What kind of event are you planning?\":\"Social Party/Celebration\"}", result);

        // an incomplete answer map is rejected
        InvalidUserAnswerException exception = assertThrows(InvalidUserAnswerException.class,
            () -> AskUserQuestionTool.builder()
                .questionHandler(questions -> Map.of())
                .build()
                .askUserQuestion(List.of(EVENT_QUESTION), null));

        assertTrue(exception.getMessage().contains("Missing answer for question"));
    }

}
