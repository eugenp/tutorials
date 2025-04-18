package com.baeldung.mapstruct.emptystringtonull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student {
    String firstName;
    String lastName;
}
