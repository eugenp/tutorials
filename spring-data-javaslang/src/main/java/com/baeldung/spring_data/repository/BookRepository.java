package com.baeldung.spring_data.repository;

import com.baeldung.spring_data.model.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javaslang.collection.Seq;
import javaslang.control.Option;

@Repository
public interface BookRepository extends JpaRepository<Book,Long>{
    Book save(Book book);
        
    Option<Book> findById(Long id);
        
    Option<Seq<Book>> findByTitleContaining(String title);

}
