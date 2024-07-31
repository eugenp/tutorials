package mocks;

public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Book getBookDetails(Long id) {
        return repository.findById(id);
    }
}
