package com.baeldung.springai.lmstudio;

import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lm-studio")
public class LmStudioController {

    private final ChatService chatService;
    private final EmbeddingService embeddingService;

    public LmStudioController(ChatService chatService, EmbeddingService embeddingService) {
        this.chatService = chatService;
        this.embeddingService = embeddingService;
    }

    @PostMapping("/chat")
    @ResponseStatus(HttpStatus.OK)
    public String chat(@RequestBody String prompt) {
        return chatService.chat(prompt);
    }

    @PostMapping("/embeddings")
    @ResponseStatus(HttpStatus.OK)
    public EmbeddingResponse getEmbeddings(@RequestBody String text) {
        return embeddingService.getEmbeddings(text);
    }
}
