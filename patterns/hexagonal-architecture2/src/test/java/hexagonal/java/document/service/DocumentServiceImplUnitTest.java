import hexagonal.java.document.model.Document;
import hexagonal.java.document.repository.DocumentRepository;
import hexagonal.java.document.service.DocumentService;
import hexagonal.java.document.service.DocumentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class DocumentServiceImplUnitTest {

    private DocumentRepository documentRepository;
    private DocumentService documentService;
    private Document firstTestDocument;
    private Document secondTestDocument;

    @BeforeEach
    void init() {
        documentRepository = mock(DocumentRepository.class);
        documentService = new DocumentServiceImpl(documentRepository);

        firstTestDocument = new Document(1L, "firstTestDocument");
        secondTestDocument = new Document(2L, "secondTestDocument");
    }

    @Test
    void testCreateDocument() {
        doNothing().when(documentRepository).createDocument(firstTestDocument);

        Assertions.assertDoesNotThrow(() -> documentService.createDocument(firstTestDocument));
    }

    @Test
    void testGetDocumentById() {
        when(documentRepository.getDocumentById(1L)).thenReturn(firstTestDocument);
        when(documentRepository.getDocumentById(2L)).thenReturn(secondTestDocument);

        Document firstDocument = documentService.getDocumentById(1L);
        Document secondDocument = documentService.getDocumentById(2L);

        Assertions.assertEquals(firstDocument, firstTestDocument);
        Assertions.assertEquals(secondDocument, secondTestDocument);

        Assertions.assertNotEquals(firstDocument, secondTestDocument);
        Assertions.assertNotEquals(secondDocument, firstTestDocument);
    }

    @Test
    void testChangeDocumentName() {
        String newDocumentName = "newName";

        doNothing().when(documentRepository).changeDocumentName(firstTestDocument.getId(), newDocumentName);

        Assertions.assertDoesNotThrow(() -> documentService.changeDocumentName(firstTestDocument.getId(), newDocumentName));
    }

    @Test
    void testDeleteDocument() {
        doNothing().when(documentRepository).deleteDocumentById(firstTestDocument.getId());

        Assertions.assertDoesNotThrow(() -> documentService.deleteDocumentById(firstTestDocument.getId()));
    }
}

