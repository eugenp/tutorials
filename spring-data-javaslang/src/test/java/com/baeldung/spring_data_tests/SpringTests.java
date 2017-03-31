package com.baeldung.spring_data_tests;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.baeldung.spring_data_app.MainApp;
import com.baeldung.spring_data.model.Book;
import com.baeldung.spring_data.model.JavaBook;
import com.baeldung.spring_data.repository.BookRepository;
import com.baeldung.spring_data.repository.JavaBookRepository;
import com.baeldung.spring_data.repository.Java8BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import javaslang.collection.Seq;
import javaslang.collection.List;
import javaslang.control.Option;

import javax.transaction.Transactional;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApp.class,webEnvironment = WebEnvironment.NONE)
public class SpringTests {
    
    @Autowired
    JavaBookRepository javaRepository;
    
    @Autowired
    Java8BookRepository java8Repository;
    
    @Autowired
    BookRepository repository;
    
    
    @Test
    public void should_return_javaslang_stream(){
        Seq authors = List.of("author1","author2");
        Book testBook = new Book();
        testBook.setTitle("Javaslang in Spring Data Javaslang Stream Test");
        testBook.setAuthors(authors);
        Book book = repository.save(testBook);
        Option<javaslang.collection.Stream<Book>> retStream = repository.findAllByTitleIsNotNull();
        assert(!retStream.isEmpty());
    }
    
    @Test
    public void should_return_seq(){
        Seq authors = List.of("author1","author2");
        Book testBook = new Book();
        testBook.setTitle("Javaslang in Spring Data Seq Test Return");
        testBook.setAuthors(authors);
        Book book = repository.save(testBook);
        Option<Seq<Book>> books = repository.findByTitleContaining("Seq Test");
        assert(!books.isEmpty());
    }
    
    
    @Test
    public void should_return_option_with_book(){
        Seq authors = List.of("author1","author2");
        Book testBook = new Book();
        testBook.setTitle("Javaslang in Spring Data");
        testBook.setAuthors(authors);
        Book book = repository.save(testBook);
        Option<Book> retBook = repository.findById(1L);
        assert(retBook.isDefined() && !retBook.isEmpty());
        assert(retBook.get() != null);
    }
    
    @Test
    public void should_return_java_list(){
        ArrayList<String> authors = new ArrayList<String>();
        authors.add("author1");
        authors.add("author2");
        JavaBook testBook = new JavaBook();
        testBook.setTitle("Javaslang in Spring Data Seq Return");
        testBook.setAuthors(authors);
        JavaBook book = javaRepository.save(testBook);
        java.util.List<JavaBook> books = javaRepository.findByTitleContaining("Seq");
        assert(!books.isEmpty());
        assert(books.size() == 1);
        assert(books.get(0).getTitle().equals("Javaslang in Spring Data Seq Return"));
    }
 
    @Test
    public void should_return_javabook(){
        ArrayList<String> authors = new ArrayList<String>();
        authors.add("author1");
        authors.add("author2");
        JavaBook testBook = new JavaBook();
        testBook.setTitle("Javaslang in Spring Data");
        testBook.setAuthors(authors);
        JavaBook book = javaRepository.save(testBook);
        JavaBook retBook = javaRepository.findById(1L);
        assert(retBook != null);
        assert(retBook.getId() == 1L);
        assert(retBook.getTitle().contains("Data"));
    }
    
    @Transactional
    @Test
    public void should_return_java8_stream(){
        ArrayList<String> authors = new ArrayList<String>();
        authors.add("author1");
        authors.add("author2");
        JavaBook testBook = new JavaBook();
        testBook.setTitle("Javaslang in Spring Data Stream");
        testBook.setAuthors(authors);
        JavaBook book = java8Repository.save(testBook);
        Optional<Stream<JavaBook>> retStream = java8Repository.findAllByTitleIsNotNull();
        assert(retStream.isPresent());
        assert(retStream.get().toArray().length > 0);
    }
    
    @Test
    public void should_return_java8_future(){
        ArrayList<String> authors = new ArrayList<String>();
        authors.add("author1");
        authors.add("author2");
        JavaBook testBook = new JavaBook();
        testBook.setTitle("Javaslang in Spring Data Stream");
        testBook.setAuthors(authors);
        JavaBook book = java8Repository.save(testBook);
        java.util.concurrent.Future<JavaBook> retBook = java8Repository.findLongRunningById(1L);
        assert(retBook != null);
        try {
            assert(retBook.get() != null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}