package com.baeldung.adapter;

import org.springframework.stereotype.Repository;

import com.baeldung.domain.Document;
import com.baeldung.domain.port.DocumentRepo;

import java.util.HashMap;
import java.util.Map;

@Repository
public class DocumentRepositoryImpl implements DocumentRepo {

    private Map<String, Document> documentMap = new HashMap<>();

    @Override
    public void storeDocument(Document document) {
        documentMap.put(document.getId(), document);
    }

    @Override
    public Document findDocumentById(String id) {
        return documentMap.get(id);
    }
}
