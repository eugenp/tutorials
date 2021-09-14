import java.util.HashMap;

public class ADatabasePort implements IPort {
    private HashMap<String, Book> books = new HashMap<String, Book>();

    public void add(Book book){
        books.put(book.getId(), book);
    }

    public Book get(String id){
        return books.get(id);
    }
}