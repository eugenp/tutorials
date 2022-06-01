package com.baeldung.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BookReview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String content;

    private int rating;

    @Column(nullable = false)
    private Long bookId;

    //

    public BookReview() {
        super();
    }

    public BookReview(String content, int rating, long bookId) {
        super();
        this.content = content;
        this.rating = rating;
        this.bookId = bookId;
    }

    //

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    //
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + (int) (bookId ^ (bookId >>> 32));
        result = (prime * result) + ((content == null) ? 0 : content.hashCode());
        result = (prime * result) + (int) (id ^ (id >>> 32));
        result = (prime * result) + rating;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BookReview other = (BookReview) obj;
        if (bookId != other.bookId) {
            return false;
        }
        if (content == null) {
            if (other.content != null) {
                return false;
            }
        } else if (!content.equals(other.content)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (rating != other.rating) {
            return false;
        }
        return true;
    }

    //
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("BookReview [id=").append(id).append(", content=").append(content).append(", rating=").append(rating).append(", bookId=").append(bookId).append("]");
        return builder.toString();
    }

}
