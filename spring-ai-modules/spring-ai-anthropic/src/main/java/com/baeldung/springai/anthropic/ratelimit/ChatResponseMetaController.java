package com.baeldung.springai.anthropic.ratelimit;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.RateLimit;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatResponseMetaController {

    private final ChatClient chatClient;

    public ChatResponseMetaController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/story-gen")
    public MetadataResponse chat(@RequestParam(defaultValue = "Tell a story in less than 100 words") String message) {

        ChatResponse chatResponse = chatClient.prompt()
            .user(message)
            .call()
            .chatResponse();

        ChatResponseMetadata metadata = chatResponse.getMetadata();
        RateLimit rateLimit = metadata.getRateLimit();
        Usage usage = metadata.getUsage();

        AssistantMessage output = chatResponse.getResult()
            .getOutput();

        return new MetadataResponse(metadata.getModel(), rateLimit.getRequestsLimit(), rateLimit.getRequestsRemaining(), rateLimit.getTokensReset(),
            rateLimit.getTokensLimit(), rateLimit.getTokensRemaining(), usage.getPromptTokens(), usage.getCompletionTokens(), output.getText());
    }
}
