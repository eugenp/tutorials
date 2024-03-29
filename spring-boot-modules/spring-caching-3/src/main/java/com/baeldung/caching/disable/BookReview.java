package com.baeldung.caching.disable;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "BOOK_REVIEWS")
public class BookReview {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_reviews_reviews_id_seq")
    @SequenceGenerator(name = "book_reviews_reviews_id_seq", sequenceName = "book_reviews_reviews_id_seq", allocationSize = 1)
    private Long reviewsId;
    private String userId;
    private String isbn;
    private String bookRating;

    public Long getReviewsId() {
        return reviewsId;
    }

    public void setReviewsId(Long reviewsId) {
        this.reviewsId = reviewsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookRating() {
        return bookRating;
    }

    public void setBookRating(String bookRating) {
        this.bookRating = bookRating;
    }

    @Override
    public String toString() {
        return "BookReview{" + "reviewsId=" + reviewsId + ", userId='" + userId + '\'' + ", isbn='" + isbn + '\'' + ", bookRating='" + bookRating + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BookReview that = (BookReview) o;
        return Objects.equals(reviewsId, that.reviewsId) && Objects.equals(userId, that.userId) && Objects.equals(isbn, that.isbn) && Objects.equals(bookRating, that.bookRating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewsId, userId, isbn, bookRating);
    }
}
