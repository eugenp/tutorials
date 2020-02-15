package com.baeldung.domain.port.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.domain.Document;
import com.baeldung.domain.port.DocumentRepo;
import com.baeldung.domain.port.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepo documentRepo;

    @Override
    public void createDocument(Document document) {
        documentRepo.storeDocument(document);
    }

    @Override
    public Document findById(String id) {
        return documentRepo.findDocumentById(id);
    }
}
