package com.baeldung.streams.parallelstream;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

public class ParallelStreamApplication {

    public long usingCollectionsParallel(Collection<Book> listOfbooks, int year) {
        AtomicLong countOfBooks = new AtomicLong();
        listOfbooks.parallelStream()
          .forEach(book -> {
              if (book.getYearPublished() == year) {
                  countOfBooks.getAndIncrement();
              }
          });
        return countOfBooks.get();
    }

    public long usingStreamParallel(Collection<Book> listOfBooks, int year) {
        AtomicLong countOfBooks = new AtomicLong();
        listOfBooks.stream()
          .parallel()
          .forEach(book -> {
              if (book.getYearPublished() == year) {
                  countOfBooks.getAndIncrement();
              }
          });
        return countOfBooks.get();
    }

    public long usingWithCustomSpliterator(MyBookContainer<Book> listOfBooks, int year) {
        AtomicLong countOfBooks = new AtomicLong();
        listOfBooks.parallelStream()
          .forEach(book -> {
              if (book.getYearPublished() == year) {
                  countOfBooks.getAndIncrement();
              }
          });
        return countOfBooks.get();
    }
}