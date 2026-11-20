package com.baeldung.springai.outputguardrail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.metadata.ChatGenerationMetadata;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.Ordered;

class OutputSafeGuardAdvisorUnitTest {

    private static final String FAILURE_RESPONSE = "Use java.time instead.";
    private static final String LEAKED_ANSWER = "Java 7 used SimpleDateFormat.";
    private static final String CLEAN_ANSWER = "LocalDate.now() returns today.";

    private OutputSafeGuardAdvisor advisor = OutputSafeGuardAdvisor.builder()
      .sensitiveWords("SimpleDateFormat", "java.util.Date")
      .failureResponse(FAILURE_RESPONSE)
      .build();

    @Test
    void givenAnswerNamingLegacyApi_whenAdvisingCall_thenAnswerIsReplaced() {
        CallAdvisorChain chain = chainReturning(generation(LEAKED_ANSWER));

        ChatClientResponse response = advisor.adviseCall(request(), chain);

        assertThat(textOf(response)).isEqualTo(FAILURE_RESPONSE);
    }

    @Test
    void givenCleanAnswer_whenAdvisingCall_thenItIsReturnedUntouched() {
        ChatClientResponse original = responseOf(generation(CLEAN_ANSWER));
        CallAdvisorChain chain = chainReturning(original);

        ChatClientResponse response = advisor.adviseCall(request(), chain);

        assertThat(response).isSameAs(original);
    }

    @Test
    void givenReplacedAnswer_whenAdvisingCall_thenMetadataIsPreserved() {
        ChatGenerationMetadata metadata = ChatGenerationMetadata.builder()
          .finishReason("STOP")
          .build();
        Generation leaked = generation(LEAKED_ANSWER, metadata);
        CallAdvisorChain chain = chainReturning(leaked);

        ChatClientResponse response = advisor.adviseCall(request(), chain);

        assertThat(response.chatResponse()
          .getResult()
          .getMetadata()).isEqualTo(metadata);
    }

    @Test
    void givenEmptyResponse_whenAdvisingCall_thenItIsReturnedUntouched() {
        ChatResponse empty = new ChatResponse(List.of());
        ChatClientResponse original = new ChatClientResponse(empty, Map.of());
        CallAdvisorChain chain = chainReturning(original);

        ChatClientResponse response = advisor.adviseCall(request(), chain);

        assertThat(response).isSameAs(original);
    }

    @Test
    void givenNoOrder_whenBuildingAdvisor_thenItRunsAfterOtherAdvisors() {
        OutputSafeGuardAdvisor defaults = OutputSafeGuardAdvisor.builder()
          .build();

        assertThat(defaults.getOrder())
          .isEqualTo(Ordered.LOWEST_PRECEDENCE - 1);
    }

    private String textOf(ChatClientResponse response) {
        return response.chatResponse()
          .getResult()
          .getOutput()
          .getText();
    }

    private Generation generation(String text) {
        return new Generation(new AssistantMessage(text));
    }

    private Generation generation(
      String text, ChatGenerationMetadata metadata) {
        return new Generation(new AssistantMessage(text), metadata);
    }

    private ChatClientResponse responseOf(Generation generation) {
        ChatResponse chatResponse = new ChatResponse(List.of(generation));

        return new ChatClientResponse(chatResponse, Map.of());
    }

    private CallAdvisorChain chainReturning(Generation generation) {
        return chainReturning(responseOf(generation));
    }

    private CallAdvisorChain chainReturning(ChatClientResponse response) {
        CallAdvisorChain chain = mock(CallAdvisorChain.class);
        when(chain.nextCall(any())).thenReturn(response);

        return chain;
    }

    private ChatClientRequest request() {
        return ChatClientRequest.builder()
          .prompt(new Prompt("How did we parse date strings in Java 7?"))
          .build();
    }
}
