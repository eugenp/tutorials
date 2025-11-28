package com.example.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.dto.PersonDTO;
import com.example.entity.Customer;
import com.example.entity.Employee;

@Mapper(componentModel = "default", uses = PersonFactory.class)
public interface PersonMapperFactory {
    PersonMapperFactory INSTANCE = Mappers.getMapper(PersonMapperFactory.class);

    @Mapping(target = "department", source = "department")
    @Mapping(target = "salary", source = "salary")
    Employee toEmployee(PersonDTO dto);

    @Mapping(target = "customerId", source = "customerId")
    @Mapping(target = "tier", source = "tier")
    Customer toCustomer(PersonDTO dto);
}


