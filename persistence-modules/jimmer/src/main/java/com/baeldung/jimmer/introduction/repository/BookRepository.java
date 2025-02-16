package com.baeldung.jimmer.introduction.repository;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.babyfish.jimmer.sql.JSqlClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.baeldung.jimmer.introduction.dto.BookView;
import com.baeldung.jimmer.introduction.models.Book;
import com.baeldung.jimmer.introduction.models.AuthorDraft;
import com.baeldung.jimmer.introduction.models.BookDraft;
import com.baeldung.jimmer.introduction.models.BookTable;
import com.baeldung.jimmer.introduction.models.Fetchers;

@Component
public class BookRepository {

    @Autowired
    private JSqlClient sqlClient;

    public void saveAdHocBookDraft(String title) {
        Book book = BookDraft.$.produce(bookDraft -> {
            bookDraft.setCreatedAt(Instant.now());
            bookDraft.setTitle(title);
            bookDraft.setAuthor(AuthorDraft.$.produce(authorDraft -> {
                authorDraft.setId(1L);
            }));
            bookDraft.setId(1L);
        });
        sqlClient.save(book);
    }

    public List<BookView> findAllByTitleLike(String title) {
        List<BookView> values = sqlClient.createQuery(BookTable.$)
          .where(BookTable.$.title()
            .like(title))
          .select(BookTable.$.fetch(BookView.class))
          .execute();

        return values;
    }

    public List<BookView> findAllByTitleLikeProjection(String title) {
        List<Book> books = sqlClient.createQuery(BookTable.$)
          .where(BookTable.$.title()
            .like(title))
          .select(BookTable.$.fetch(Fetchers.BOOK_FETCHER.title()
            .createdAt()
            .author()))
          .execute();

        return books.stream()
          .map(BookView::new)
          .collect(Collectors.toList());
    }
}
