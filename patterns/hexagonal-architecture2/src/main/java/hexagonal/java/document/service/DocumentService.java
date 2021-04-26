package hexagonal.java.document.service;

import hexagonal.java.document.model.Document;

public interface DocumentService {
    void createDocument(Document document);
    Document getDocumentById(Long documentId);
    void changeDocumentName(Long documentId, String newName);
    void deleteDocumentById(Long documentId);
}
