package com.baeldung.pattern.hexagonal.architecture.application.factory;

import com.baeldung.pattern.hexagonal.architecture.application.dto.AddressDto;
import com.baeldung.pattern.hexagonal.architecture.domain.model.Address;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressFactory {

    public static Address create(AddressDto addressDto) {
        Address address = new Address();
        address.setCity(addressDto.getCity());
        address.setHouse(addressDto.getHouse());
        address.setName(addressDto.getName());
        address.setStreet(addressDto.getStreet());
        address.setZip(addressDto.getZip());
        return address;
    }
}
