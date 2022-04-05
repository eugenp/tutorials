package com.baeldung.morphia.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import dev.morphia.annotations.Embedded;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Field;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Index;
import dev.morphia.annotations.IndexOptions;
import dev.morphia.annotations.Indexes;
import dev.morphia.annotations.Property;
import dev.morphia.annotations.Reference;
import dev.morphia.annotations.Validation;

@Entity("Books")
@Indexes({ @Index(fields = @Field("title"), options = @IndexOptions(name = "book_title")) })
@Validation("{ price : { $gt : 0 } }")
public class Book {
    @Id
    private String isbn;
    @Property
    private String title;
    private String author;
    @Embedded
    private Publisher publisher;
    @Property("price")
    private double cost;
    @Reference
    private Set<Book> companionBooks;
    @Property
    private LocalDateTime publishDate;

    public Book() {

    }

    public Book(String isbn, String title, String author, double cost, Publisher publisher) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.cost = cost;
        this.publisher = publisher;
        this.companionBooks = new HashSet<>();
    }

    // Getters and setters ...
    public String getIsbn() {
        return isbn;
    }

    public Book setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public Book setPublisher(Publisher publisher) {
        this.publisher = publisher;
        return this;
    }

    public double getCost() {
        return cost;
    }

    public Book setCost(double cost) {
        this.cost = cost;
        return this;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public Book setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
        return this;
    }

    public Set<Book> getCompanionBooks() {
        return companionBooks;
    }

    public Book addCompanionBooks(Book book) {
        if (companionBooks != null)
            this.companionBooks.add(book);
        return this;
    }

    @Override
    public String toString() {
        return "Book [isbn=" + isbn + ", title=" + title + ", author=" + author + ", publisher=" + publisher + ", cost=" + cost + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        long temp;
        temp = Double.doubleToLongBits(cost);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
        result = prime * result + ((publisher == null) ? 0 : publisher.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Book other = (Book) obj;
        if (author == null) {
            if (other.author != null)
                return false;
        } else if (!author.equals(other.author))
            return false;
        if (Double.doubleToLongBits(cost) != Double.doubleToLongBits(other.cost))
            return false;
        if (isbn == null) {
            if (other.isbn != null)
                return false;
        } else if (!isbn.equals(other.isbn))
            return false;
        if (publisher == null) {
            if (other.publisher != null)
                return false;
        } else if (!publisher.equals(other.publisher))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

}
