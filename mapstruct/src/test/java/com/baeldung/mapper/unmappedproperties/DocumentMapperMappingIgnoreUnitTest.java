package com.baeldung.mapper.unmappedproperties;

import com.baeldung.unmappedproperties.dto.DocumentDTO;
import com.baeldung.unmappedproperties.entity.Document;
import com.baeldung.unmappedproperties.mapper.DocumentMapperMappingIgnore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class DocumentMapperMappingIgnoreUnitTest {

    @Test
    public void givenDocumentEntityToDocumentDto_whenMaps_thenCorrect() {
        Document entity = new Document();
        entity.setId(1);
        entity.setTitle("Price 13-42");
        entity.setText("List of positions.......");
        entity.setModificationTime(new Date());

        DocumentDTO dto = DocumentMapperMappingIgnore.INSTANCE.documentToDocumentDTO(entity);

        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getTitle()).isEqualTo(entity.getTitle());
        assertThat(dto.getText()).isEqualTo(entity.getText());
    }

    @Test
    public void givenDocumentDtoToDocumentEntity_whenMaps_thenCorrect() {
        DocumentDTO dto = new DocumentDTO();
        dto.setId(1);
        dto.setTitle("Price 13-42");
        dto.setText("List of positions.......");
        dto.setComments(Arrays.asList("Not all positions", "Wrong price values"));
        dto.setAuthor("Author1");

        Document entity = DocumentMapperMappingIgnore.INSTANCE.documentDTOToDocument(dto);

        assertThat(entity.getId()).isEqualTo(dto.getId());
        assertThat(entity.getTitle()).isEqualTo(dto.getTitle());
        assertThat(entity.getText()).isEqualTo(dto.getText());
    }
}