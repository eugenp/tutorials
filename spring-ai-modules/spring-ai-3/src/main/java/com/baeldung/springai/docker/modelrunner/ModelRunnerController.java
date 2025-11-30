package com.baeldung.springai.docker.modelrunner;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ModelRunnerController {

    private final ChatClient chatClient;

    public ModelRunnerController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message) {
        return this.chatClient.prompt()
          .user(message)
          .call()
          .content();
    }

}