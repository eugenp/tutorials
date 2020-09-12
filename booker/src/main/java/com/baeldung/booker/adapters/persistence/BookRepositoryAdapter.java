package com.baeldung.booker.adapters.persistence;

import com.baeldung.booker.domain.Book;
import com.baeldung.booker.ports.outgoing.IFindBook;
import com.baeldung.booker.ports.outgoing.ISaveBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class BookRepositoryAdapter implements IFindBook, ISaveBook {

  @Autowired
  BookRepository repository;

  @Override
  public Book findById(Long id) {
    return repository.findById(id).orElseThrow(NoSuchElementException::new);
  }

  @Override
  public List<Book> findAll() {
    return repository.findAll();
  }

  @Override
  public Book saveBook(Book book) {
    return repository.save(book);
  }
}
