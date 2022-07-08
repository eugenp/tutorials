package com.baeldung.idc;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import io.github.serpro69.kfaker.Faker;

/**
 * Repository for storing the books.
 *
 * It serves just for illustrative purposes and is completely in-memory. It uses Java Faker library in order to generate data.
 *
 * @author A.Shcherbakov
 *
 */
@Component
public class BookRepository {

    private List<Book> items;

    @PostConstruct
    public void init() {
        Faker faker = new Faker();
        final io.github.serpro69.kfaker.provider.Book book = faker.getBook();
        this.items = IntStream.range(1, faker.getRandom()
            .nextInt(10, 20))
            .mapToObj(i -> new Book(i, book.title(), book.author(), book.genre()))
            .collect(Collectors.toList());

    }

    public int getCount() {
        return items.size();
    }

    public List<Book> getItems() {
        return items;
    }

    public Optional<Book> getById(int id) {
        return this.items.stream()
            .filter(item -> id == item.getId())
            .findFirst();
    }

    public void save(int id, Book book) {
        IntStream.range(0, items.size())
            .filter(i -> items.get(i)
                .getId() == id)
            .findFirst()
            .ifPresent(i -> this.items.set(i, book));
    }

}
