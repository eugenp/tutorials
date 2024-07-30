package com.baeldung.shallow_deep;

import com.example.yourapp.model.DocDescription;
import com.example.yourapp.model.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CopyDocumentTest {

    @Test
    void givenOriginalDocument_whenSetDocId_thenOriginalShouldRemainUnchanged() {
        // Given
        Document originalDocument = new Document(123, new DocDescription(56, "XLS"));
        CopyDocument copyDocument = new CopyDocument(originalDocument);
        
        // When
        copyDocument.setDocId(456);

        // Then
        assertEquals(456, copyDocument.getDocId(), "CopyDocument should reflect the updated document ID.");
        assertEquals(123, originalDocument.getDocId(), "Original document ID should remain unchanged.");
    }

    @Test
    void givenOriginalDocument_whenSetDocIdMultipleTimes_thenOnlyFirstChangeShouldAffectCopy() {
        // Given
        Document originalDocument = new Document(123, new DocDescription(56, "XLS"));
        CopyDocument copyDocument = new CopyDocument(originalDocument);
        
        // When
        copyDocument.setDocId(456); // First change
        copyDocument.setDocId(789); // Second change

        // Then
        assertEquals(789, copyDocument.getDocId(), "CopyDocument should reflect the last updated document ID.");
        assertEquals(123, originalDocument.getDocId(), "Original document ID should remain unchanged after multiple changes.");
    }
}
