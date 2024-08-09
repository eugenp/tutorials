package com.baeldung.shallow_deep.utils;

import com.example.yourapp.model.DocDescription;
import com.example.yourapp.model.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeepCopyUtilTest {

    @Test
    void givenOriginalDocument_whenDeepCopy_thenOriginalShouldRemainUnchanged() {
        // Given
        Document originalDocument = new Document(123, new DocDescription(56, "XLS"));
        
        // When
        Document deepCopyDocument = DeepCopyUtil.deepCopy(originalDocument, Document.class);
        deepCopyDocument.getDocDescription().setType("PDF");

        // Then
        assertEquals("XLS", originalDocument.getDocDescription().getType(), "Original document should remain unchanged.");
        assertEquals("PDF", deepCopyDocument.getDocDescription().getType(), "Deep copy should have the modified type.");
    }

    @Test
    void givenNullObject_whenDeepCopy_thenShouldReturnNull() {
        // When
        Document deepCopyDocument = DeepCopyUtil.deepCopy(null, Document.class);

        // Then
        assertNull(deepCopyDocument, "Deep copy of null should return null.");
    }
}
