package com.baeldung.springai.evaluator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

@Component
class VectorStoreInitializer implements ApplicationRunner {

    private final VectorStore vectorStore;
    private final ResourcePatternResolver resourcePatternResolver;

    public VectorStoreInitializer(VectorStore vectorStore, ResourcePatternResolver resourcePatternResolver) {
        this.vectorStore = vectorStore;
        this.resourcePatternResolver = resourcePatternResolver;
    }

    @Override
    public void run(ApplicationArguments args) throws IOException {
        List<Document> documents = new ArrayList<>();
        Resource[] resources = resourcePatternResolver.getResources("classpath:documents/*.md");
        Arrays.stream(resources).forEach(resource -> {
            MarkdownDocumentReader markdownDocumentReader = new MarkdownDocumentReader(resource, MarkdownDocumentReaderConfig.defaultConfig());
            documents.addAll(markdownDocumentReader.read());
        });
        vectorStore.add(new TokenTextSplitter().split(documents));
    }

}