package com.baeldung.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.baeldung.dto.CustomerDto;
import com.baeldung.entity.Customer;

@Mapper
public interface CustomerDtoMapper {

    @Mapping(source = "firstName", target = "forename")
    @Mapping(source = "lastName", target = "surname")
    CustomerDto from(Customer customer);
}
