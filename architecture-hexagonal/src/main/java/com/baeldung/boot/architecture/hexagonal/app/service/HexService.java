package com.baeldung.boot.architecture.hexagonal.app.service;

import com.baeldung.boot.architecture.hexagonal.domain.entity.Book;
import com.baeldung.boot.architecture.hexagonal.domain.entity.User;

import java.util.List;

/**
 * @author Thanos Floros (thanosfloros@strong-programmer.com)
 */
public interface HexService {

    List<User> getUsers();

    List<Book> getBooks();

    void insertData();
}
