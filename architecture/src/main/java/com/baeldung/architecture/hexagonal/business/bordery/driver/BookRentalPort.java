package com.baeldung.architecture.hexagonal.business.bordery.driver;

import java.util.List;

import com.baeldung.architecture.hexagonal.business.bordery.dto.BookDto;

public interface BookRentalPort {

    List<BookDto> searchByName(String partOfTheName);

    BookDto donate(BookDto book);
}
