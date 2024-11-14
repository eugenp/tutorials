package com.baeldung.springai.chromadb;

import java.util.ArrayList;
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
        List<Document> documents = new ArrayList<>();
        PoetryFetcher
            .fetch()
            .forEach(poetry -> {
                Map<String, Object> metadata = Map.of("title", poetry.title());
                String content = String.join("", poetry.lines());

                Document document = new Document(content, metadata);
                documents.add(document);
            });
        vectorStore.add(documents);
    }

}