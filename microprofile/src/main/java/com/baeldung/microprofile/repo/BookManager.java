package com.baeldung.microprofile.repo;

import com.baeldung.microprofile.model.Book;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@ApplicationScoped
public class BookManager {

    private AtomicInteger bookId = new AtomicInteger(0);

    private ConcurrentMap<String, Book> inMororyDb = new ConcurrentHashMap<>();

    public String add(Book book) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        String date = LocalDate.now().format(formatter);
        String id = String.format("%03d-%s", bookId.incrementAndGet(),date);
        book.setId(id);
        inMororyDb.putIfAbsent(id, book);
        return id;
    }

    public Book get(String id) {
        return inMororyDb.get(id);
    }

    public List<Book> list() {
        return inMororyDb.values().stream().collect(Collectors.toList());
    }

    public void deleteAll() {
        Iterator<String> it = inMororyDb.keySet().iterator();
        while (it.hasNext()) {
            inMororyDb.remove(it);
        }
    }
}
