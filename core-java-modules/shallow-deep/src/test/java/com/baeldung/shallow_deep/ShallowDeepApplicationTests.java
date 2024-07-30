package com.baeldung.shallow_deep;

import com.example.yourapp.model.DocDescription;
import com.example.yourapp.model.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShallowDeepApplicationTests {

    @Test
    void givenOriginalDocument_whenCreateShallowCopy_thenBothShouldReflectChanges() {
        // Given
        Document originalDocument = new Document(123, new DocDescription(56, "XLS"));
        
        // When
        Document shallowCopyDocument = new Document(originalDocument);
        shallowCopyDocument.getDocDescription().setType("PDF");

        // Then
        assertEquals("PDF", originalDocument.getDocDescription().getType(), "Original document should reflect changes made to shallow copy.");
        assertEquals("PDF", shallowCopyDocument.getDocDescription().getType(), "Shallow copy should have the modified type.");
    }

    @Test
    void givenOriginalDocument_whenCreateDeepCopy_thenOriginalShouldRemainUnchanged() {
        // Given
        Document originalDocument = new Document(123, new DocDescription(56, "XLS"));
        
        // When
        Document deepCopyDocument = DeepCopyUtil.deepCopy(originalDocument, Document.class);
        deepCopyDocument.getDocDescription().setType("PDF");

        // Then
        assertEquals("XLS", originalDocument.getDocDescription().getType(), "Original document should remain unchanged.");
        assertEquals("PDF", deepCopyDocument.getDocDescription().getType(), "Deep copy should have the modified type.");
    }
}
