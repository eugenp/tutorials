package com.baeldung.chatbot.mongodb.repositories;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModelName;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ArticlesRepository {
    private static final Logger log = LoggerFactory.getLogger(ArticlesRepository.class);

    private final EmbeddingStore<TextSegment> embeddingStore;
    private final EmbeddingModel embeddingModel;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ArticlesRepository(@Value("${app.load-articles}") Boolean shouldLoadArticles,
      EmbeddingStore<TextSegment> embeddingStore, EmbeddingModel embeddingModel) throws IOException {
        this.embeddingStore = embeddingStore;
        this.embeddingModel = embeddingModel;

        if (shouldLoadArticles) {
            loadArticles();
        }
    }

    private void loadArticles() throws IOException {
        String resourcePath = "articles.json";
        int maxTokensPerChunk = 8000;
        int overlapTokens = 800;

        List<TextSegment> documents = loadJsonDocuments(resourcePath, maxTokensPerChunk, overlapTokens);

        log.info("Documents to store: " + documents.size());

        for (TextSegment document : documents) {
            Embedding embedding = embeddingModel.embed(document.text()).content();
            embeddingStore.add(embedding, document);
        }

        log.info("Documents are uploaded");
    }

    private List<TextSegment> loadJsonDocuments(String resourcePath, int maxTokensPerChunk, int overlapTokens) throws IOException {

        InputStream inputStream = ArticlesRepository.class.getClassLoader().getResourceAsStream(resourcePath);

        if (inputStream == null) {
            throw new FileNotFoundException("Resource not found: " + resourcePath);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        int batchSize = 500;
        List<Document> batch = new ArrayList<>();
        List<TextSegment> textSegments = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            JsonNode jsonNode = objectMapper.readTree(line);

            String title = jsonNode.path("title").asText(null);
            String body = jsonNode.path("body").asText(null);
            JsonNode metadataNode = jsonNode.path("metadata");

            if (body != null) {
                addDocumentToBatch(title, body, metadataNode, batch);

                if (batch.size() >= batchSize) {
                    textSegments.addAll(splitIntoChunks(batch, maxTokensPerChunk, overlapTokens));
                    batch.clear();
                }
            }
        }

        if (!batch.isEmpty()) {
            textSegments.addAll(splitIntoChunks(batch, maxTokensPerChunk, overlapTokens));
        }

        return textSegments;
    }

    private void addDocumentToBatch(String title, String body, JsonNode metadataNode, List<Document> batch) {
        String text = (title != null ? title + "\n\n" + body : body);

        Metadata metadata = new Metadata();
        if (metadataNode != null && metadataNode.isObject()) {
            Iterator<String> fieldNames = metadataNode.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                metadata.put(fieldName, metadataNode.path(fieldName).asText());
            }
        }

        Document document = Document.from(text, metadata);
        batch.add(document);
    }

    private List<TextSegment> splitIntoChunks(List<Document> documents, int maxTokensPerChunk, int overlapTokens) {
        OpenAiTokenizer tokenizer = new OpenAiTokenizer(OpenAiEmbeddingModelName.TEXT_EMBEDDING_3_SMALL);

        DocumentSplitter splitter = DocumentSplitters.recursive(
                maxTokensPerChunk,
                overlapTokens,
                tokenizer
        );

        List<TextSegment> allSegments = new ArrayList<>();
        for (Document document : documents) {
            List<TextSegment> segments = splitter.split(document);
            allSegments.addAll(segments);
        }

        return allSegments;
    }
}
