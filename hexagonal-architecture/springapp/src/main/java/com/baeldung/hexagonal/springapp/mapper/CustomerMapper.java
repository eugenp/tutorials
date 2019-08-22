package com.baeldung.hexagonal.springapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.baeldung.hexagonal.core.Customer;
import com.baeldung.hexagonal.springapp.entity.CustomerEntity;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerEntity toEntity(Customer model);

    default Customer toModel(CustomerEntity entity) {
        return new Customer(entity.getId(), entity.getEmail());
    }

}
