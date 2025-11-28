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

   Person toPerson(PersonDTO dto);

    @ObjectFactory
    default Person createPerson(PersonDTO dto) {
        if ("employee".equalsIgnoreCase(dto.getType())) return new Employee();
        else if ("customer".equalsIgnoreCase(dto.getType())) return new Customer();
        throw new IllegalArgumentException("Unknown type: " + dto.getType());
    }
}

