package org.baeldung.persistence.criteria.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.baeldung.persistence.criteria.model.Book;
import org.baeldung.persistence.criteria.repository.BookRepositoryCustom;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl implements BookRepositoryCustom {

    private EntityManager em;

    public BookRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Book> findBooksByAuthorNameAndTitle(String authorName, String title) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);

        Root<Book> book = cq.from(Book.class);
        List<Predicate> predicates = new ArrayList<>();

        if (authorName != null) {
            predicates.add(cb.equal(book.get("author"), authorName));
        }
        if (title != null) {
            predicates.add(cb.like(book.get("title"), "%" + title + "%"));
        }
        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Book> query = em.createQuery(cq);
        return query.getResultList();
    }

}
