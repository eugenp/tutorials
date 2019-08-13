package com.baeldung.mapper.unmapped;

import com.baeldung.dto.DocumentDTO;
import com.baeldung.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DocumentMapper {
    DocumentMapper INSTANCE = Mappers.getMapper(DocumentMapper.class);

    DocumentDTO documentToDocumentDTO(Document entity);
    Document documentDTOToDocument(DocumentDTO dto);
}
