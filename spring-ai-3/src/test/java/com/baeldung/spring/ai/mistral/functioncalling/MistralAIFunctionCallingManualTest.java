package com.baeldung.spring.ai.mistral.functioncalling;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Set;

@Import(MistralAIFunctionConfiguration.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MistralAIFunctionCallingManualTest {

    private static final Logger logger = LoggerFactory.getLogger(MistralAIFunctionCallingManualTest.class);
    
    @Autowired
    private MistralAiChatModel chatClient;

    @Test
    void givenMistralAiChatClient_whenAskChatAPIAboutPatientHealthStatus_thenExpectedHealthStatusIsPresentInResponse() {
        var options = MistralAiChatOptions.builder()
          .withFunction("retrievePatientHealthStatus")
          .build();

        ChatResponse paymentStatusResponse = chatClient.call(
          new Prompt("What's the health status of the patient with id P004?",  options));

        String responseContent = paymentStatusResponse.getResult().getOutput().getText();
        logger.info(responseContent);

        Assertions.assertThat(responseContent)
          .containsIgnoringCase("has increased blood pressure");
    }

    @Test
    void givenMistralAiChatClient_whenAskChatAPIAboutPatientHealthStatusAndWhenThisStatusWasChanged_thenExpectedInformationInResponse() {
        var options = MistralAiChatOptions.builder()
          .withFunctions(
            Set.of("retrievePatientHealthStatus",
              "retrievePatientHealthStatusChangeDate"))
          .build();

        ChatResponse paymentStatusResponse = chatClient.call(
          new Prompt(
            "What's the health status of the patient with id P005",
            options));

        String paymentStatusResponseContent = paymentStatusResponse.getResult()
          .getOutput().getText();
        logger.info(paymentStatusResponseContent);

        Assertions.assertThat(paymentStatusResponseContent)
          .containsIgnoringCase("healthy");

        ChatResponse changeDateResponse = chatClient.call(
           new Prompt(
             "When health status of the patient with id P005 was changed?",
             options));

        String changeDateResponseContent = changeDateResponse.getResult().getOutput().getContent();
        logger.info(changeDateResponseContent);

        Assertions.assertThat(paymentStatusResponseContent)
          .containsIgnoringCase("June 1, 2024");
    }

    @Test
    void givenHttpClient_whenSendTheRequestToChatAPI_thenShouldBeExpectedWordInResponse() throws IOException, InterruptedException {

        String apiKey = System.getenv("MISTRAL_API_KEY");
        String apiUrl = "https://api.mistral.ai/v1/chat/completions";
        String requestBody = "{"
          + "\"model\": \"mistral-large-latest\","
          + "\"messages\": [{\"role\": \"user\", "
          + "\"content\": \"What the patient health statuses can be?\"}]"
          + "}";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(apiUrl))
          .header("Content-Type", "application/json")
          .header("Accept", "application/json")
          .header("Authorization", "Bearer " + apiKey)
          .POST(HttpRequest.BodyPublishers.ofString(requestBody))
          .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();
        logger.info("Model response: " + responseBody);

        Assertions.assertThat(responseBody)
          .containsIgnoringCase("healthy");
    }
}
