package com.suri.blogic.ports;
  
import com.suri.model.Book;

public interface BookOpsPort {
    String addBook(Book book);
    String removeBook(Book book);
}
