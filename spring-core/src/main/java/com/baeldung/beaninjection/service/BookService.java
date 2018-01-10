package com.baeldung.beaninjection.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import com.baeldung.beaninjection.model.*;

/**
 * This class is the service for Book Services.
 * @Author Akshay Desale.
 */
@Component
public class BookService implements ApplicationContextAware {

    ApplicationContext applicationContext;
    Map<String, Book> books = new HashMap<>();

    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    
    public Book getBook(String title) {
        return books.get(title);
    }

    
    public List<Book> getAllBooks() {
        if (books.size() == 0)
            return null;

        return books.entrySet()
            .parallelStream()
            .map(x -> x.getValue())
            .collect(Collectors.toList());
    }

    
    public boolean addBook(String title, String author) {
        Book book = applicationContext.getBean(Book.class);
        book.setTitle(title);
        book.setAuthor(author);
        
        if (books.putIfAbsent(title, book) == null)
            return true;
        else
            return false;
    }

    
    public boolean removeBook(String title) {
        if (books.remove(title) != null)
            return true;
        else
            return false;
    }

    
    public boolean removeAllBooks() {
        books.clear();
        return true;
    }

}
