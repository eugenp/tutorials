package com.baeldung.unmappedproperties.mapper;

import com.baeldung.unmappedproperties.dto.DocumentDTO;
import com.baeldung.unmappedproperties.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DocumentMapperUnmappedPolicy {

    DocumentMapperUnmappedPolicy INSTANCE = Mappers.getMapper(DocumentMapperUnmappedPolicy.class);

    DocumentDTO documentToDocumentDTO(Document entity);

    Document documentDTOToDocument(DocumentDTO dto);
}