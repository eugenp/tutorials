package com.baeldung.spring_data_tests;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.baeldung.spring_data_app.MainApp;
import com.baeldung.spring_data.model.Book;
import com.baeldung.spring_data.model.JavaBook;
import com.baeldung.spring_data.repository.BookRepository;
import com.baeldung.spring_data.repository.JavaBookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import javaslang.collection.Seq;
import javaslang.collection.List;
import javaslang.control.Option;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApp.class,webEnvironment = WebEnvironment.NONE)
public class SpringTests {
    
    @Autowired
    JavaBookRepository javaRepository;
    
    @Autowired
    BookRepository repository;
    
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
    public void should_return_list(){
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
    public void should_return_book(){
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
}