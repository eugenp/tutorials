package com.baeldung.mapper.unmapped;

import com.baeldung.dto.DocumentDTO;
import com.baeldung.entity.Document;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertEquals;

class DocumentMapperWithConfigUnitTest {
    @Test
    void givenDocumentEntityToDocumentDto_whenMaps_thenCorrect() {
        Document entity = new Document();
        entity.setId(1);
        entity.setTitle("Price 13-42");
        entity.setText("List of positions.......");
        entity.setModificationTime(new Date());

        DocumentDTO dto = DocumentMapperWithConfig.INSTANCE.documentToDocumentDTO(entity);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getTitle(), entity.getTitle());
        assertEquals(dto.getText(), entity.getText());
    }

    @Test
    void givenDocumentDtoToDocumentEntity_whenMaps_thenCorrect() {

        DocumentDTO dto = new DocumentDTO();
        dto.setId(1);
        dto.setTitle("Price 13-42");
        dto.setText("List of positions.......");
        dto.setComments(Arrays.asList("Not all positions", "Wrong price values"));

        Document entity = DocumentMapperWithConfig.INSTANCE.documentDTOToDocument(dto);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getTitle(), dto.getTitle());
        assertEquals(entity.getText(), dto.getText());

    }
}