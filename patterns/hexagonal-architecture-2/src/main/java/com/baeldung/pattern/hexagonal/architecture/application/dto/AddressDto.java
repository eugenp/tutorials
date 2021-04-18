package com.baeldung.pattern.hexagonal.architecture.application.dto;

import com.baeldung.pattern.hexagonal.architecture.domain.model.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private Long id;
    private String name;
    private String house;
    private String street;
    private String city;
    private String zip;

    public static AddressDto of(Address address) {
        return AddressDto.builder()
            .id(address.getId())
            .city(address.getCity())
            .zip(address.getZip())
            .street(address.getStreet())
            .house(address.getHouse())
            .name(address.getName())
            .build();
    }
}
