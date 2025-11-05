package com.baeldung.criteriabuilder;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class BookRepositoryImpl implements BookRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    public long countAllBooks() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(Book.class)));
        return entityManager.createQuery(cq)
          .getSingleResult();
    }

    public long countBooksByTitle(String titleKeyword) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Book> bookRoot = cq.from(Book.class);
        Predicate titleCondition = cb.like(bookRoot.get("title"), "%" + titleKeyword + "%");
        cq.where(titleCondition);
        cq.select(cb.count(bookRoot));
        return entityManager.createQuery(cq)
          .getSingleResult();
    }

    public long countBooksByAuthor(String authorName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Book> bookRoot = cq.from(Book.class);
        Predicate authorCondition = cb.equal(bookRoot.get("author"), authorName);
        cq.where(authorCondition);
        cq.select(cb.count(bookRoot));
        return entityManager.createQuery(cq)
          .getSingleResult();
    }

    public long countBooksByTitleAndAuthor(String titleKeyword, String authorName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Book> bookRoot = cq.from(Book.class);
        Predicate titleCondition = cb.like(bookRoot.get("title"), "%" + titleKeyword + "%");
        Predicate authorCondition = cb.equal(bookRoot.get("author"), authorName);
        cq.where(cb.and(titleCondition, authorCondition));
        cq.select(cb.count(bookRoot));
        return entityManager.createQuery(cq)
          .getSingleResult();
    }

    @Override
    public long countBooksByAuthorOrYear(String authorName, int publishYear) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Book> bookRoot = cq.from(Book.class);

        Predicate authorCondition = cb.equal(bookRoot.get("author"), authorName);
        Predicate yearCondition = cb.greaterThanOrEqualTo(bookRoot.get("publishYear"), publishYear);

        cq.where(cb.or(authorCondition, yearCondition));
        cq.select(cb.count(bookRoot));

        return entityManager.createQuery(cq)
          .getSingleResult();
    }

    public long countBooksByTitleOrYearAndAuthor(String authorName, int publishYear, String titleKeyword) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Book> bookRoot = cq.from(Book.class);

        Predicate authorCondition = cb.equal(bookRoot.get("author"), authorName);
        Predicate yearCondition = cb.equal(bookRoot.get("publishYear"), publishYear);
        Predicate titleCondition = cb.like(bookRoot.get("title"), "%" + titleKeyword + "%");

        Predicate authorAndYear = cb.and(authorCondition, yearCondition);
        cq.where(cb.or(authorAndYear, titleCondition));
        cq.select(cb.count(bookRoot));

        return entityManager.createQuery(cq)
          .getSingleResult();
    }
}
