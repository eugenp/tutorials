package com.baeldung.springai.chatclient.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/articles")
public class BlogsController {

    public static final String DEFAULT_QUESTION = "Can you suggest articles to learn Spring Boot?";
    private final ChatClient chatClient;
    private final SimpleVectorStore vectorStore;

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
            .chatResponse()
            .getResult()
            .getOutput()
            .getContent();
    }

    @GetMapping("v2")
    List<RecommendedArticle> askForRecommendation(@RequestParam(required = false, name = "question", defaultValue = DEFAULT_QUESTION) String question) {
        return chatClient.prompt()
            .user(question)
            .call()
            .entity(new ParameterizedTypeReference<List<RecommendedArticle>>() {

            });
    }

    @GetMapping("v3")
    List<RecommendedArticle> get3(@RequestParam(required = false, name = "question", defaultValue = DEFAULT_QUESTION) String question) {

        return chatClient.prompt()
            .advisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()))
            .user(question)
            .call()
            .entity(new ParameterizedTypeReference<List<RecommendedArticle>>() {

            });
    }

    record RecommendedArticle(String title, List<String> tags) {

    }

}
