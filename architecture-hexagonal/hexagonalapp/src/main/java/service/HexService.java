package service;

import com.baeldung.boot.architecture.hexagonal.domain.Book;
import com.baeldung.boot.architecture.hexagonal.domain.User;

import java.util.List;

/**
 * @author Thanos Floros (thanosfloros@strong-programmer.com)
 */
public interface HexService {

    List<User> getUsers();

    List<Book> getBooks();

    void insertData();
}
