public class BookService {
    private IPort dao;

    public BookService(IPort bookDao) {
        dao = bookDao;
    }

    public void add(Book book) {
        dao.add(book);
    }

    public Book search(String id) {
        return dao.get(id);
    }
}