package com.baeldung.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.baeldung.context.MappingContext;
import com.baeldung.dto.CustomerDto;
import com.baeldung.entity.Customer;

@Mapper
public interface CustomerDtoMapper {

    @Mapping(source = "firstName", target = "forename")
    @Mapping(source = "lastName", target = "surname")
    CustomerDto from(Customer customer);

    @Mapping(source = "firstName", target = "forename")
    @Mapping(source = "lastName", target = "surname")
    CustomerDto from(Customer customer, @Context MappingContext context);

    @AfterMapping
    default void normalize(@MappingTarget CustomerDto dto, @Context MappingContext context) {
        dto.setForename(context.normalizeName(dto.getForename()));
        dto.setSurname(context.normalizeName(dto.getSurname()));
    }
}
