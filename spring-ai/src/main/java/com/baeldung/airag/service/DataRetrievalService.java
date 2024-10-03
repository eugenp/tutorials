package com.baeldung.airag.service;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataRetrievalService {
    @Autowired
    private VectorStore vectorStore;

    public List<Document> searchData(String query) {
        return vectorStore.similaritySearch(query);
    }
}
