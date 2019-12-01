package com.baeldung.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.pojo.Book;

@Repository
public interface BookRepository extends CrudRepository<Book,String> {

}
