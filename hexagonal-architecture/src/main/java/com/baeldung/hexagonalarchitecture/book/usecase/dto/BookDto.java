package com.baeldung.hexagonalarchitecture.book.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookDto {
    Long id;
    String name;
}
