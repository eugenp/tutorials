package com.baeldung.criteriabuilder;

public interface BookRepositoryCustom {
    long countAllBooks();
    long countBooksByTitle(String titleKeyword);
    long countBooksByAuthor(String authorName);
    long countBooksByTitleAndAuthor(String titleKeyword, String authorName);
    long countBooksByAuthorOrYear(String authorName, int publishYear);
    long countBooksByTitleOrYearAndAuthor(String authorName, int publishYear, String titleKeyword);
}
