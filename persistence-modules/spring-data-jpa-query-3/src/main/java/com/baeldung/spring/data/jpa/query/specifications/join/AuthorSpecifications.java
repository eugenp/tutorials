package com.baeldung.spring.data.jpa.query.specifications.join;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class AuthorSpecifications {

    public static Specification<Author> hasFirstNameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.<String>get("firstName"), "%" + name + "%");
    }

    public static Specification<Author> hasLastName(String name) {
        return (root, query, cb) -> cb.equal(root.<String>get("lastName"), name);
    }

    public static Specification<Author> hasBookWithTitle(String bookTitle) {
        return (root, query, criteriaBuilder) -> {
            Join<Book, Author> authorsBook = root.join("books");
            return criteriaBuilder.equal(authorsBook.get("title"), bookTitle);
        };
    }

}