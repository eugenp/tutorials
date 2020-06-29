package com.baeldung.mapstruct.mappingCollections.mapper;

import com.baeldung.mapstruct.mappingCollections.dto.CompanyDTO;
import com.baeldung.mapstruct.mappingCollections.model.Company;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface CompanyMapperAdderPreferred {

    CompanyDTO map(Company company);
}
