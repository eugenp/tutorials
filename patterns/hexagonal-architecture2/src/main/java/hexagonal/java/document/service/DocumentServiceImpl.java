package hexagonal.java.document.service;

import hexagonal.java.document.model.Document;
import hexagonal.java.document.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl implements DocumentService {

    private DocumentRepository documentRepository;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public void createDocument(Document document) {
        documentRepository.createDocument(document);
    }

    @Override
    public Document getDocumentById(Long documentId) {
        return documentRepository.getDocumentById(documentId);
    }

    @Override
    public void changeDocumentName(Long documentId, String newName) {
        documentRepository.changeDocumentName(documentId, newName);
    }

    @Override
    public void deleteDocumentById(Long documentId) {
        documentRepository.deleteDocumentById(documentId);
    }
}
