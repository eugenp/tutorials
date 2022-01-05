package com.demo.repositories;


import com.demo.models.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;


public interface BookCrudRepository extends CrudRepository<Book, UUID> {

}
