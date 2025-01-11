package com.baeldung.spring.data.jpa.optionalfields;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.persistence.Tuple;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.spring.data.jpa.optionalfields.dto.BookDtoRepository;
import com.baeldung.spring.data.jpa.optionalfields.objects.BookObjectsRepository;
import com.baeldung.spring.data.jpa.optionalfields.projection.BookProjection;
import com.baeldung.spring.data.jpa.optionalfields.projection.BookProjectionRepository;
import com.baeldung.spring.data.jpa.optionalfields.sqlmapping.BookSqlMappingRepository;
import com.baeldung.spring.data.jpa.optionalfields.objects.BookTupleRepository;

@ActiveProfiles("h2")
@SpringBootTest(classes = OptionalFieldsApplication.class)
@Transactional
public class OptionalFieldsUnitTest {
    @Autowired
    private BookProjectionRepository bookProjectionRepository;

    @Autowired
    private BookDtoRepository bookDtoRepository;

    @Autowired
    private BookObjectsRepository bookObjectsRepository;

    @Autowired
    private BookTupleRepository bookTupleRepository;

    @Autowired
    private BookSqlMappingRepository bookSqlMappingRepository;

    @Test
    public void whenUseProjection_thenFetchOnlyProjectionAttributes() {
        String title = "Title Projection";
        String author = "Author Projection";

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        bookProjectionRepository.save(book);

        List<BookProjection> result = bookProjectionRepository.fetchBooks();

        assertEquals(1, result.size());
        assertEquals(title, result.get(0)
          .getTitle());
        assertEquals(author, result.get(0)
          .getAuthor());
    }

    @Test
    public void whenUseDto_thenFetchOnlyDtoAttributes() {
        String title = "Title Dto";
        String author = "Author Dto";

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        bookDtoRepository.save(book);

        List<BookDto> result = bookDtoRepository.fetchBooks();

        assertEquals(1, result.size());
        assertEquals(title, result.get(0)
          .title());
        assertEquals(author, result.get(0)
          .author());
    }

    @Test
    public void whenUseSqlMapping_thenFetchOnlyColumnResults() {
        String title = "Title Sql Mapping";
        String author = "Author Sql Mapping";

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        bookSqlMappingRepository.save(book);

        List<BookDto> result = bookSqlMappingRepository.fetchBooks();

        assertEquals(1, result.size());
        assertEquals(title, result.get(0)
          .title());
        assertEquals(author, result.get(0)
          .author());
    }

    @Test
    public void whenUseObjectArray_thenFetchOnlyQueryFields() {
        String title = "Title Object";
        String author = "Author Object";

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        bookObjectsRepository.save(book);

        List<Object[]> result = bookObjectsRepository.fetchBooks();

        assertEquals(1, result.size());
        assertEquals(3, result.get(0).length);
        assertEquals(title, result.get(0)[1].toString());
        assertEquals(author, result.get(0)[2].toString());
    }

    @Test
    public void whenUseTuple_thenFetchOnlyQueryFields() {
        String title = "Title Tuple";
        String author = "Author Tuple";

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        bookTupleRepository.save(book);

        List<Tuple> result = bookTupleRepository.fetchBooks();

        assertEquals(1, result.size());
        assertEquals(3, result.get(0)
          .toArray().length);
        assertEquals(title, result.get(0)
          .get("title"));
        assertEquals(author, result.get(0)
          .get("author"));
    }
}
