package com.baeldung.modelmapper.advanced_mapping;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class Name {
    String firstName;
    String lastName;
}
