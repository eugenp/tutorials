package com.baeldung.jhipster6.service.impl;

import com.baeldung.jhipster6.service.BookService;
import com.baeldung.jhipster6.domain.Book;
import com.baeldung.jhipster6.repository.BookRepository;
import com.baeldung.jhipster6.service.dto.BookDTO;
import com.baeldung.jhipster6.service.mapper.BookMapper;
import com.baeldung.jhipster6.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Book.
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    /**
     * Save a book.
     *
     * @param bookDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BookDTO save(BookDTO bookDTO) {
        log.debug("Request to save Book : {}", bookDTO);
        Book book = bookMapper.toEntity(bookDTO);
        book = bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    /**
     * Get all the books.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> findAll() {
        log.debug("Request to get all Books");
        return bookRepository.findAll().stream()
            .map(bookMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one book by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BookDTO> findOne(Long id) {
        log.debug("Request to get Book : {}", id);
        return bookRepository.findById(id)
            .map(bookMapper::toDto);
    }

    /**
     * Delete the book by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Book : {}", id);
        bookRepository.deleteById(id);
    }

    @Override
    public Optional<BookDTO> purchase(Long id) {
        Optional<BookDTO> bookDTO = findOne(id);
        if(bookDTO.isPresent()) {
            int quantity = bookDTO.get().getQuantity();
            if(quantity > 0) {
                bookDTO.get().setQuantity(quantity - 1);
                Book book = bookMapper.toEntity(bookDTO.get());
                book = bookRepository.save(book);
                return bookDTO;
            }
            else {
                throw new BadRequestAlertException("Book is not in stock", "book", "notinstock");
            }
        }
        return Optional.empty();
    }
}
