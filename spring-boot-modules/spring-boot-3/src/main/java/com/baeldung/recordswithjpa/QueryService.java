package com.baeldung.recordswithjpa;

import com.baeldung.recordswithjpa.entity.Book;
import com.baeldung.recordswithjpa.records.BookRecord;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryService {
    @PersistenceContext
    private EntityManager entityManager;

    public List<BookRecord> findAllBooks() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookRecord> query = cb.createQuery(BookRecord.class);
        Root<Book> root = query.from(Book.class);
        query.select(cb
          .construct(BookRecord.class, root.get("id"), root.get("title"), root.get("author"), root.get("isbn")));
        return entityManager.createQuery(query).getResultList();
    }

    public BookRecord findBookByTitle(String title) {
        TypedQuery<BookRecord> query = entityManager
            .createQuery("SELECT new com.baeldung.recordswithjpa.records.BookRecord(b.id, b.title, b.author, b.isbn) " +
                         "FROM Book b WHERE b.title = :title", BookRecord.class);
        query.setParameter("title", title);
        return query.getSingleResult();
    }


    public List<BookRecord> findAllBooksUsingMapping() {
        Query query = entityManager.createNativeQuery("SELECT * FROM book", "BookRecordMapping");
        return query.getResultList();
    }
}