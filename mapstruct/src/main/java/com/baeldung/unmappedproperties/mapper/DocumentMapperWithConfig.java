package com.baeldung.unmappedproperties.mapper;

import com.baeldung.unmappedproperties.dto.DocumentDTO;
import com.baeldung.unmappedproperties.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface DocumentMapperWithConfig {

    DocumentMapperWithConfig INSTANCE = Mappers.getMapper(DocumentMapperWithConfig.class);

    DocumentDTO documentToDocumentDTO(Document entity);

    Document documentDTOToDocument(DocumentDTO dto);
}