package com.baeldung.jhipster6.service.mapper;

import com.baeldung.jhipster6.domain.*;
import com.baeldung.jhipster6.service.dto.BookDTO;
import com.baeldung.jhipster6.domain.Book;

import org.mapstruct.*;

/**
 * Mapper for the entity Book and its DTO BookDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BookMapper extends EntityMapper<BookDTO, Book> {



    default Book fromId(Long id) {
        if (id == null) {
            return null;
        }
        Book book = new Book();
        book.setId(id);
        return book;
    }
}
