package com.baeldung.springai.rag.mongodb.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import com.baeldung.springai.rag.mongodb.dto.WikiDocument;

@Component
public class WikiDocumentsRepository {
    private final VectorStore vectorStore;

    public WikiDocumentsRepository(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void saveWikiDocument(WikiDocument wikiDocument) {

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("filePath", wikiDocument.getFilePath());
        Document document = new Document(wikiDocument.getContent(), metadata);
        List<Document> documents = new TokenTextSplitter().apply(List.of(document));

        vectorStore.add(documents);
    }

    public List<WikiDocument> findSimilarDocuments(String searchText) {

        return vectorStore
          .similaritySearch(SearchRequest.builder()
            .query(searchText)
            .similarityThreshold(0.87)
            .topK(10).build())
          .stream()
          .map(document -> {
              WikiDocument wikiDocument = new WikiDocument();
              wikiDocument.setFilePath((String) document
                .getMetadata().get("filePath"));
              wikiDocument.setContent(document.getText());

              return wikiDocument;
          })
          .toList();
    }
}
