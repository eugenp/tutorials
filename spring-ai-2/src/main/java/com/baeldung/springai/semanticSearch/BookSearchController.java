package com.baeldung.springai.semanticSearch;


import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookSearchController {
    private final VectorStore vectorStore;
    private final OllamaChatModel ollamaChatClient;

    public BookSearchController(VectorStore vectorStore, OllamaChatModel ollamaChatClient) {
        this.vectorStore = vectorStore;
        this.ollamaChatClient = ollamaChatClient;
    }

    @PostMapping("/search")
    List<String> semanticSearch(@RequestBody String query) {
        return vectorStore.similaritySearch(SearchRequest.builder()
                        .query(query)
                        .topK(3)
                        .build())
                .stream()
                .map(Document::getText)
                .toList();
    }

    @PostMapping("/enhanced-search")
    String enhancedSearch(@RequestBody String query) {
        String context = vectorStore.similaritySearch(SearchRequest.builder()
                        .query(query)
                        .topK(3)
                        .build())
                .stream()
                .map(Document::getText)
                .reduce("", (a, b) -> a + b + "\n");


        var generalInstructionsSystemMessage = new SystemMessage(context);
        var currentPromptMessage = new UserMessage(query);

        var prompt = new Prompt(List.of(generalInstructionsSystemMessage, currentPromptMessage));

        return ollamaChatClient.call(prompt).getResult().getOutput().getText();
    }

}
