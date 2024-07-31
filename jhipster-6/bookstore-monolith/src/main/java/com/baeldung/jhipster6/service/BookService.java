package com.baeldung.jhipster6.service;

import com.baeldung.jhipster6.service.dto.BookDTO;

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

    /**
     * Simulates purchasing a book by reducing the stock of a book by 1.
     * @param id the id of the book
     * @return Updated BookDTO, empty if not found, or throws exception if an error occurs.
     */
    Optional<BookDTO> purchase(Long id);
}
