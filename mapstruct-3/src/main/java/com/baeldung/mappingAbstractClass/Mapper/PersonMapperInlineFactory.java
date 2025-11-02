package com.example.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

import com.example.dto.PersonDTO;
import com.example.entity.Customer;
import com.example.entity.Employee;
import com.example.entity.Person;

@Mapper
public interface PersonMapperInlineFactory {
    PersonMapperInlineFactory INSTANCE = Mappers.getMapper(PersonMapperInlineFactory.class);

    @Mapping(target = "department")
    @Mapping(target = "salary")
    Employee toEmployee(PersonDTO dto);

    @Mapping(target = "customerId")
    @Mapping(target = "tier")
    Customer toCustomer(PersonDTO dto);

    @ObjectFactory
    default Person createPerson(Class<? extends Person> targetType, PersonDTO dto) {
        if ("employee".equalsIgnoreCase(dto.getType())) return new Employee();
        else if ("customer".equalsIgnoreCase(dto.getType())) return new Customer();
        throw new IllegalArgumentException("Unknown type: " + dto.getType());
    }
}

