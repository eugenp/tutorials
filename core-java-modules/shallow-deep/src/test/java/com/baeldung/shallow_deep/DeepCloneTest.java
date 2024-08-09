package com.baeldung.shallow_deep;

import com.baeldung.shallow_deep.models.DocDescription;
import com.baeldung.shallow_deep.models.Document;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeepCloneTest {

    @Test
    void givenOriginalDocument_whenCloned_thenCloneShouldBeEqualButNotSame() throws CloneNotSupportedException {
        // Given
        Document originalDocument = new Document(123, new DocDescription(56, "XLS"));
        
        // When
        Document deepCopyDocument = (Document) originalDocument.clone();

        // Then
        assertEquals(originalDocument.getDocId(), deepCopyDocument.getDocId(), "Cloned document should have the same ID as the original.");
        assertEquals(originalDocument.getDocDescription().getType(), deepCopyDocument.getDocDescription().getType(), "Cloned document should have the same type as the original.");
        assertNotSame(originalDocument, deepCopyDocument, "Cloned document should not be the same instance as the original.");
    }

    @Test
    void givenOriginalDocument_whenCloned_thenModifyingCloneShouldNotAffectOriginal() throws CloneNotSupportedException {
        // Given
        Document originalDocument = new Document(123, new DocDescription(56, "XLS"));
        
        // When
        Document deepCopyDocument = (Document) originalDocument.clone();
        deepCopyDocument.getDocDescription().setType("PDF");

        // Then
        assertEquals("XLS", originalDocument.getDocDescription().getType(), "Original document should remain unchanged when the clone is modified.");
        assertEquals("PDF", deepCopyDocument.getDocDescription().getType(), "Cloned document should reflect the changes made to it.");
    }
}
