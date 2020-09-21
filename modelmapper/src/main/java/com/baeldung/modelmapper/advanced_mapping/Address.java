package com.baeldung.modelmapper.advanced_mapping;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class Address {
    String street;
    String city;
    int streetNo;
}
