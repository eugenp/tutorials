package com.baeldung.springai.semanticsearch;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
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
    private final ChatClient chatClient;

    public BookSearchController(VectorStore vectorStore, ChatClient.Builder chatClientBuilder) {
        this.vectorStore = vectorStore;
        this.chatClient = chatClientBuilder.build();
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

        return chatClient.prompt()
            .system(context)
            .user(query)
            .call()
            .content();
    }

}
