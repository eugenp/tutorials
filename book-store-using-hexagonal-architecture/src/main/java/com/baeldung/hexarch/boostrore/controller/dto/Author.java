package com.baeldung.hexarch.boostrore.controller.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

@Value
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Author {
    String firstName;
    String lastName;
    @EqualsAndHashCode.Include
    String emailId;
}
