package hexagonal.java.document.repository;

import hexagonal.java.document.model.Document;

public interface DocumentRepository {
    void createDocument(Document document);
    Document getDocumentById(Long documentId);
    void changeDocumentName(Long documentId, String newName);
    void deleteDocumentById(Long documentId);
}
