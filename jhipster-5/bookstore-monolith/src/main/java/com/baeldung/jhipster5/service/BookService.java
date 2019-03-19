package com.baeldung.jhipster5.service;

import com.baeldung.jhipster5.service.dto.BookDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Book.
 */
public interface BookService {

    /**
     * Save a book.
     *
     * @param bookDTO the entity to save
     * @return the persisted entity
     */
    BookDTO save(BookDTO bookDTO);

    /**
     * Get all the books.
     *
     * @return the list of entities
     */
    List<BookDTO> findAll();


    /**
     * Get the "id" book.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BookDTO> findOne(Long id);

    /**
     * Delete the "id" book.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
