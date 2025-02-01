package com.baeldung.springai.rag.mongodb.service;

import com.baeldung.springai.rag.mongodb.dto.WikiDocument;
import com.baeldung.springai.rag.mongodb.repository.WikiDocumentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class WikiDocumentsServiceImpl {
    private final WikiDocumentsRepository wikiDocumentsRepository;

    public WikiDocumentsServiceImpl(WikiDocumentsRepository wikiDocumentsRepository) {
        this.wikiDocumentsRepository = wikiDocumentsRepository;
    }

    public void saveWikiDocument(String filePath) {
        try {
            String content = Files.readString(Path.of(filePath));
            WikiDocument wikiDocument = new WikiDocument();
            wikiDocument.setFilePath(filePath);
            wikiDocument.setContent(content);

            wikiDocumentsRepository.saveWikiDocument(wikiDocument);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<WikiDocument> findSimilarDocuments(String searchText) {
        return wikiDocumentsRepository.findSimilarDocuments(searchText);
    }
}
