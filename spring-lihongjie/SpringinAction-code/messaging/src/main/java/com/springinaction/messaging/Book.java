package com.springinaction.messaging;

import java.io.Serializable;
import java.math.BigDecimal;

public class Book implements Serializable {
  private static final long serialVersionUID = 1L;
  
  public Book() {}
  
  public Book(String title, String author, BigDecimal price) {
    this.title = title;
    this.author = author;
    this.price = price;
  }

  private String title;
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
  public BigDecimal getPrice() {
    return price;
  }
  public void setPrice(BigDecimal price) {
    this.price = price;
  }
  public static long getSerialversionuid() {
    return serialVersionUID;
  }
  private String author;
  private BigDecimal price;
}
