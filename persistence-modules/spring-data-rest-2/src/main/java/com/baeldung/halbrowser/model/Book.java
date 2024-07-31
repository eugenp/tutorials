package com.baeldung.halbrowser.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull
  @Column(columnDefinition = "VARCHAR", length = 100)
  private String title;

  @NotNull
  @Column(columnDefinition = "VARCHAR", length = 100)
  private String author;

  @Column(columnDefinition = "VARCHAR", length = 1000)
  private String blurb;

  private int pages;

  public Book() {
  }

  public Book(@NotNull String title, @NotNull String author, String blurb, int pages) {
    this.title = title;
    this.author = author;
    this.blurb = blurb;
    this.pages = pages;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getBlurb() {
    return blurb;
  }

  public void setBlurb(String blurb) {
    this.blurb = blurb;
  }

  public int getPages() {
    return pages;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }

  @Override
  public String toString() {
    return "Book{" +
      "id=" + id +
      ", title='" + title + '\'' +
      ", author='" + author + '\'' +
      ", blurb='" + blurb + '\'' +
      ", pages=" + pages +
      '}';
  }
}
