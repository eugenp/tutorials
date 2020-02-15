package com.baeldung.domain.port;

import com.baeldung.domain.Document;

public interface DocumentService {

    void createDocument(Document document);

    Document findById(String id);

}
