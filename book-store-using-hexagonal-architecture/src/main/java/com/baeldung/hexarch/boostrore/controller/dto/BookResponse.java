package com.baeldung.hexarch.boostrore.controller.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Set;

@Value
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BookResponse {
    @EqualsAndHashCode.Include
    String isbn;
    String title;
    Set<Author> authors;
}
