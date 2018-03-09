package footmark.springdata.jpa.domain.domain3;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-demo-cfg.xml");
        AuthorRepository authorRepository = ctx.getBean("authorRepository", AuthorRepository.class);
        BookRepository bookRepository = ctx.getBean("bookRepository", BookRepository.class);

        //persist
        Author author1 = new Author("John Smith");
        Author author2 = new Author("Michelle Diangello");
        Author author3 = new Author("Mark Armstrong");

        Book book1 = new Book("Day Dreaming");
        Book book2 = new Book("Day Dreaming, Seconod Edition");

        author1.addBook(book1);

        author2.addBook(book1);

        author1.addBook(book2);
        author2.addBook(book2);
        author3.addBook(book2);


//        Author author = authorRepository.save(author1);
//        authorRepository.save(author2);
//        authorRepository.save(author3);
//        Book book = bookRepository.save(book1);

        //Dissociating one side of the many-to-many association

//        author1.remove();
        authorRepository.delete(author2);
//        bookRepository.delete(book1);
    }
}
