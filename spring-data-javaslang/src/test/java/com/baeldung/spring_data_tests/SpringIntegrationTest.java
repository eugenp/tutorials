package com.baeldung.spring_data_tests;

import com.baeldung.spring_data.model.Book;
import com.baeldung.spring_data.model.JavaBook;
import com.baeldung.spring_data.repository.BookRepository;
import com.baeldung.spring_data.repository.JavaBookRepository;
import com.baeldung.spring_data_app.MainApp;
import javaslang.collection.List;
import javaslang.collection.Seq;
import javaslang.control.Option;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApp.class,webEnvironment = WebEnvironment.NONE)
public class SpringIntegrationTest {
    
    @Autowired
    private JavaBookRepository javaRepository;
    
    @Autowired
    private BookRepository repository;
    
    @Test
    public void should_return_seq(){
        Seq authors = List.of("author1","author2");
        Book testBook = new Book();
        testBook.setTitle("Javaslang in Spring Data Seq Test Return");
        testBook.setAuthors(authors);
        Book book = repository.save(testBook);
        Option<Seq<Book>> books = repository.findByTitleContaining("Seq Test");

        assertThat(books).isNotEmpty();
    }
    
    
    @Test
    public void should_return_option_with_book(){
        Seq authors = List.of("author1","author2");
        Book testBook = new Book();
        testBook.setTitle("Javaslang in Spring Data");
        testBook.setAuthors(authors);
        Book book = repository.save(testBook);
        Option<Book> retBook = repository.findById(1L);

        assertThat(retBook.isDefined()).isTrue();
        assertThat(retBook).isNotEmpty();
    }
    
    @Test
    public void should_return_list(){
        ArrayList<String> authors = new ArrayList<String>();
        authors.add("author1");
        authors.add("author2");
        JavaBook testBook = new JavaBook();
        testBook.setTitle("Javaslang in Spring Data Seq Return");
        testBook.setAuthors(authors);
        JavaBook book = javaRepository.save(testBook);
        java.util.List<JavaBook> books = javaRepository.findByTitleContaining("Seq");
        assertThat(books)
          .isNotEmpty()
          .hasSize(1)
          .extracting("title")
          .contains("Javaslang in Spring Data Seq Return");
    }
 
    @Test
    public void should_return_book(){
        ArrayList<String> authors = new ArrayList<String>();
        authors.add("author1");
        authors.add("author2");
        JavaBook testBook = new JavaBook();
        testBook.setTitle("Javaslang in Spring Data");
        testBook.setAuthors(authors);
        JavaBook book = javaRepository.save(testBook);
        JavaBook retBook = javaRepository.findById(1L);

        assertThat(retBook.getId()).isEqualTo(1L);
        assertThat(retBook.getTitle()).isEqualTo("Javaslang in Spring Data");
    }
}