package com.baeldung.springai.session;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.session.CreateSessionRequest;
import org.springframework.ai.session.Session;
import org.springframework.ai.session.SessionEvent;
import org.springframework.ai.session.SessionService;
import org.springframework.ai.session.compaction.CompactionResult;
import org.springframework.ai.session.compaction.RecursiveSummarizationCompactionStrategy;
import org.springframework.ai.session.compaction.SlidingWindowCompactionStrategy;
import org.springframework.ai.session.compaction.TurnCountTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "GEMINI_API_KEY", matches = ".*")
class SpringAiSessionLiveTest {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatModel chatModel;

    @Test
    void givenSession_whenAppendingMessages_thenStoredInOrder() {
        Session session = sessionService.create(CreateSessionRequest.builder()
            .userId("alice")
            .build());

        sessionService.appendMessage(session.id(), new UserMessage("What is Spring AI?"));
        sessionService.appendMessage(session.id(), new AssistantMessage("It's an application framework for AI engineering."));

        List<Message> messages = sessionService.getMessages(session.id());
        assertThat(messages).hasSize(2);
        assertThat(messages.get(0).getMessageType()).isEqualTo(MessageType.USER);
        assertThat(messages.get(1).getMessageType()).isEqualTo(MessageType.ASSISTANT);
    }

    @Test
    void givenMultiTurnConversation_whenCompacting_thenOlderEventsAreArchived() {
        Session session = sessionService.create(CreateSessionRequest.builder()
            .userId("alice")
            .build());
        for (int turn = 1; turn <= 4; turn++) {
            sessionService.appendMessage(session.id(), new UserMessage("Question " + turn));
            sessionService.appendMessage(session.id(), new AssistantMessage("Answer " + turn));
        }

        CompactionResult result = sessionService.compact(session.id(),
            new TurnCountTrigger(2),
            SlidingWindowCompactionStrategy.builder()
                .maxEvents(4)
                .build());

        assertThat(result.eventsRemoved()).isPositive();
        assertThat(sessionService.getEvents(session.id())).hasSameSizeAs(result.compactedEvents());
    }

    @Test
    void givenSessionId_whenChattingAcrossTurns_thenContextIsRemembered() {
        chatService.chat("session-abc", "My name is Yadier, remember it.");

        String response = chatService.chat("session-abc", "What is my name?");

        assertThat(response).containsIgnoringCase("Yadier");
        assertThat(sessionService.getMessages("session-abc")).hasSize(4);
    }

    @Test
    void givenLongConversation_whenSummarizing_thenOlderEventsAreReplacedBySummary() {
        Session session = sessionService.create(CreateSessionRequest.builder()
            .userId("alice")
            .build());
        for (int turn = 1; turn <= 4; turn++) {
            sessionService.appendMessage(session.id(), new UserMessage("Question " + turn));
            sessionService.appendMessage(session.id(), new AssistantMessage("Answer " + turn));
        }

        ChatClient chatClient = ChatClient.builder(chatModel).build();
        CompactionResult result = sessionService.compact(session.id(),
            new TurnCountTrigger(2),
            RecursiveSummarizationCompactionStrategy.builder(chatClient)
                .maxEventsToKeep(4)
                .build());

        SessionEvent summary = result.compactedEvents().stream()
            .filter(SessionEvent::isSynthetic)
            .findFirst()
            .orElseThrow();

        assertThat(result.eventsRemoved()).isPositive();
        assertThat(summary.getMessage().getText()).isNotBlank();
    }

}
