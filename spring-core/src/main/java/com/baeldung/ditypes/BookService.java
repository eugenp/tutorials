package com.baeldung.ditypes;

public interface BookService{
  public Boolean create(Book book);
  public Book read(String isbn);
  public Boolean update(String isbn, Book book);
  public Boolean delete(String isbn);
}
