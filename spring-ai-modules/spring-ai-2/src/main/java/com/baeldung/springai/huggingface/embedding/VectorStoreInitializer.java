package com.baeldung.springai.huggingface.embedding;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class VectorStoreInitializer implements ApplicationRunner {

    private final VectorStore vectorStore;

    public VectorStoreInitializer(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(ApplicationArguments args) {
        List<Document> documents = QuoteFetcher
            .fetch()
            .stream()
            .map(quote -> {
                Map<String, Object> metadata = Map.of("author", quote.author());
                return new Document(quote.quote(), metadata);
            })
            .toList();
        vectorStore.add(documents);
    }

}