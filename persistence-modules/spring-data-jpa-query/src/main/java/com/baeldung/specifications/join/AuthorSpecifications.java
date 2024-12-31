package com.baeldung.specifications.join;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;

public class AuthorSpecifications {

    public static Specification<Author> hasFirstNameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.<String>get("firstName"), "%" + name + "%");
    }

    public static Specification<Author> hasLastName(String name) {
        return (root, query, cb) -> cb.equal(root.<String>get("lastName"), name);
    }

    public static Specification<Author> hasBookWithTitle(String bookTitle) {
        return (root, query, criteriaBuilder) -> {
            Join<BookAuthorEntity, Author> authorsBook = root.join("bookAuthorEntities");
            return criteriaBuilder.equal(authorsBook.get("title"), bookTitle);
        };
    }

}