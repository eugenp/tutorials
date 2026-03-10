package com.baeldung.springai.explainableaiagents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.augment.AugmentedToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientHealthStatusService {
    private static final Logger log = LoggerFactory.getLogger(PatientHealthStatusService.class);
    private final ChatClient chatClient;

    @Autowired
    public PatientHealthStatusService(OpenAiChatModel model) {
        AugmentedToolCallbackProvider<AgentThinking> provider = AugmentedToolCallbackProvider.<AgentThinking>builder()
          .toolObject(new PatientHealthInformationTools())
          .argumentType(AgentThinking.class)
          .argumentConsumer(event -> {
              AgentThinking thinking = event.arguments();
              log.info("Chosen tool: {}\n LLM Reasoning: {}\n Confidence: {}",
                      event.toolDefinition().name(), thinking.innerThought(), thinking.confidence());
          })
          .build();

        chatClient = ChatClient.builder(model)
          .defaultToolCallbacks(provider)
          .build();
    }

    public String getPatientStatusInformation(String prompt) {
        log.info("Input request: {}", prompt);
        return chatClient.prompt(prompt)
          .call()
          .content();
    }
}
