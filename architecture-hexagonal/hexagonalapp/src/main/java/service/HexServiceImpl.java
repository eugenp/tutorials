package service;

import com.baeldung.boot.architecture.hexagonal.domain.Book;
import com.baeldung.boot.architecture.hexagonal.domain.User;
import com.baeldung.boot.architecture.hexagonal.repository.BookRepository;
import com.baeldung.boot.architecture.hexagonal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Thanos Floros (thanosfloros@strong-programmer.com)
 */
public class HexServiceImpl implements HexService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void insertData() {
        User user = new User();
        user.setFirstName("cooldude");
        user.setLastName("awesomefirstname");
        userRepository.save(user);

        Book book = new Book();

    }
}
