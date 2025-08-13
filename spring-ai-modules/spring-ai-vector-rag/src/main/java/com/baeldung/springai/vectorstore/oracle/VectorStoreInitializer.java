package com.baeldung.springai.vectorstore.oracle;

import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
class VectorStoreInitializer implements ApplicationRunner {

    private final VectorStore vectorStore;

    VectorStoreInitializer(VectorStore vectorStore) {
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