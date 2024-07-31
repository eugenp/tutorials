package com.baeldung.mapstruct.mappingCollections.mapper;

import com.baeldung.mapstruct.mappingCollections.dto.CompanyDTO;
import com.baeldung.mapstruct.mappingCollections.model.Company;
import org.mapstruct.Mapper;

@Mapper(uses = EmployeeMapper.class)
public interface CompanyMapper {

    CompanyDTO map(Company company);
}
