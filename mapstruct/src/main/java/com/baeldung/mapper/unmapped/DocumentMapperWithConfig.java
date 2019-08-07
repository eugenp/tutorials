package com.baeldung.mapper.unmapped;

import com.baeldung.dto.DocumentDTO;
import com.baeldung.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface DocumentMapperWithConfig {

    DocumentMapperWithConfig INSTANCE = Mappers.getMapper(DocumentMapperWithConfig.class);

    DocumentDTO documentToDocumentDTO(Document entity);
    Document documentDTOToDocument(DocumentDTO dto);
}
