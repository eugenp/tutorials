package com.baeldung.spring_data_javaslang.repository;

import com.baeldung.spring_data_javaslang.model.Book;


import javaslang.collection.Seq;
import javaslang.control.Option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long>{
    Book save(Book book);
        
    Option<Book> findById(Long id);
        
    Seq<Book> findByTitleContaining(String title);

}
