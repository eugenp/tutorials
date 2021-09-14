package com.baeldung.boot.hexagonal.domain.ports.service;

import com.baeldung.boot.hexagonal.domain.models.Book;

import java.util.ArrayList;
import java.util.List;

public class BooksTestUtility {
    public static List<Book> populateBooks() {

        List<Book> books = new ArrayList<>();
        books.add(new Book().withId(1L)
          .withTitle("Spring in Action")
          .withAuthors(new String[] { "Craig Walls" })
          .withPrice(31.19)
          .withIsbn("ISBN 9781617297571")
          .withPublisher("Manning Publications")
          .withDescription(
            "The latest version of a bestseller upgraded for Spring 5.3 and Spring Boot 2.4, Spring in Action, Sixth Edition also covers the RSocket specification for reactive networking between applications and delves deep into essential features of Spring Security."));
        books.add(new Book().withId(2L)
          .withTitle("Effective Java")
          .withAuthors(new String[] { "Joshua Bloc" })
          .withPrice(29.45)
          .withPublisher("Addison-Wesley; 3rd edition")
          .withIsbn("ISBN 978-0134685991")
          .withDescription("Java has changed dramatically since the previous edition of Effective Java was published shortly after the release of Java 6."));
        books.add(new Book().withId(3L)
          .withTitle("Spring Microservices in Action")
          .withAuthors(new String[] { "John Carnell", "Illary Huaylupo Sánchez " })
          .withPrice(32.32)
          .withIsbn("ISBN 978-1617296956")
          .withPublisher("Manning Publications")
          .withDescription("Spring Microservices in Action, Second Edition teaches you to build microservice-based applications using Java and Spring."));
        return books;
    }

}
