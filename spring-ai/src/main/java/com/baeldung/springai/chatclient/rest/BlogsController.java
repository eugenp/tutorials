package com.baeldung.springai.chatclient.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/articles")
public class BlogsController {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public BlogsController(ChatClient.Builder chatClientBuilder, EmbeddingModel embeddingModel) throws IOException {
        this.chatClient = chatClientBuilder.build();
        this.vectorStore = new SimpleVectorStore(embeddingModel);
        initContext();
    }

    private void initContext() throws IOException {
        List<Document> documents = Files.readAllLines(Path.of("src/main/resources/articles.txt"))
            .stream()
            .map(Document::new)
            .toList();

        vectorStore.add(documents);
    }

    @GetMapping("v1")
    String askQuestion(@RequestParam(name = "question") String question) {
        return chatClient.prompt()
            .user(question)
            .call()
            .content();
    }

    record Article(String title, Set<String> tags) {
    }

    @GetMapping("v2")
    List<Article> askQuestionAndRetrieveArticles(@RequestParam(name = "question") String question) {
        return chatClient.prompt()
            .user(question)
            .call()
            .entity(new ParameterizedTypeReference<List<Article>>() {});
    }

    @GetMapping("v3")
    List<Article> askQuestionWithContext(@RequestParam(name = "question") String question) {
        return chatClient.prompt()
            .advisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()))
            .user(question)
            .call()
            .entity(new ParameterizedTypeReference<List<Article>>() {});
    }

}
