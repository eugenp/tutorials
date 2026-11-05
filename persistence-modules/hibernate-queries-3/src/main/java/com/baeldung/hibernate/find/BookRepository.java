package com.baeldung.hibernate.find;

import java.util.List;
import java.util.Optional;

import jakarta.annotation.Nullable;
import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.data.repository.OrderBy;
import jakarta.data.repository.Repository;
import org.hibernate.annotations.processing.Find;

@Repository
public interface BookRepository {
    @Find
    @OrderBy(value = "title", descending = true)
    @OrderBy("author$name")
    List<Book> getAllBooks();

    @Find
    List<Book> getAllBooks(Order<Book> sort);

    @Find
    @OrderBy("title")
    Page<Book> getBooksPage(PageRequest pageRequest);

    @Find
    Book getBookWithTitle(String title);

    @Find
    Optional<Book> getOptionalBookWithTitle(String title);

    @Find
    @Nullable
    Book getNullableBookWithTitle(String title);

    @Find
    List<Book> getAllBooksByAuthorName(String author$name);

}
