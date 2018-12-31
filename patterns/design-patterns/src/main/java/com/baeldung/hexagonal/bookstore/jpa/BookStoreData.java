package com.baeldung.hexagonal.bookstore.jpa;

import java.util.Optional;

import com.baeldung.hexagonal.bookstore.entity.Book;

public class BookStoreData {

    public static Book getBook(String isbn) {
        Optional<Book> book = getBookDao().get(isbn);
        return book.orElseGet(() -> {
            return null;
        });
    }

    private static class JpaBookDaoHolder {
        private static final Dao<Book> jpaBookDao = new JpaBookDao(new JpaEntityManagerFactory().getEntityManager());
    }

    public static Dao getBookDao() {
        return JpaBookDaoHolder.jpaBookDao;
    }
}
