package com.baeldung.springai.chromadb;

import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class VectorStoreInitializer implements ApplicationRunner {

    private final VectorStore vectorStore;

    public VectorStoreInitializer(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(ApplicationArguments args) {
        List<Document> documents = PoetryFetcher
            .fetch()
            .stream()
            .map(poem -> {
                Map<String, Object> metadata = Map.of("title", poem.title());
                String content = String.join("\n", poem.lines());

                return new Document(content, metadata);
            })
            .toList();
        vectorStore.add(documents);
    }

}