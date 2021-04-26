package hexagonal.java.document.repository;

import hexagonal.java.document.model.Document;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class DocumentRepositoryImpl implements DocumentRepository{

    private Map<Long, Document> documentDatabase = new HashMap<>();

    public void createDocument(Document document) {
        documentDatabase.put(document.getId(), document);
        printDatabase();
    }

    public Document getDocumentById(Long documentId) {
        return documentDatabase.get(documentId);
    }

    public void changeDocumentName(Long documentId, String newName) {
        documentDatabase.get(documentId).setName(newName);
        printDatabase();
    }

    public void deleteDocumentById(Long documentId) {
        documentDatabase.remove(documentId);
        printDatabase();
    }

    private void printDatabase() {
        for (Map.Entry<Long, Document> entry : documentDatabase.entrySet()) {
            System.out.println("id: " + entry.getKey() + " id: " + entry.getValue().getId() + " name: " + entry.getValue().getName());
        }
    }
}
