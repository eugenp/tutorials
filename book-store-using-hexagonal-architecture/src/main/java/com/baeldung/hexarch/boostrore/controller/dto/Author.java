package com.baeldung.hexarch.boostrore.controller.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Author {
    String firstName;
    String lastName;
    @EqualsAndHashCode.Include
    String emailId;
}
