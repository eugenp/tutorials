package com.baeldung.application;

import com.baeldung.domain.model.CoffeeOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class VirtualOrderRepositoryUnitTest {

    VirtualOrderRepositoryAdapter repository;

    @BeforeEach
    void setUp() {
        repository = new VirtualOrderRepositoryAdapter();
    }

    @Test
    void whenCreatingMultipleEntries_thenReturnIncrementalId() {
        CoffeeOrder coffeeOrder1 = new CoffeeOrder();
        CoffeeOrder coffeeOrder2 = new CoffeeOrder();
        int id1 = repository.create(coffeeOrder1);
        int id2 = repository.create(coffeeOrder2);

        Assertions.assertEquals(id2 - 1, id1);
    }

    @Test
    void whenCreatingEntry_thenFindAllReturnsIt() {
        CoffeeOrder coffeeOrder = new CoffeeOrder();
        repository.create(coffeeOrder);

        Assertions.assertFalse(repository.findAll().isEmpty());
    }

    @Test
    void whenCreatingEntry_thenFindByIdReturnsIt() {
        CoffeeOrder coffeeOrder = new CoffeeOrder();
        int id = repository.create(coffeeOrder);

        Optional<CoffeeOrder> maybeEntry = repository.findById(id);
        Assertions.assertTrue(maybeEntry.isPresent());
    }

    @Test
    void whenUpdatingNonExistingId_thenThrowIllegalStateException() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            repository.update(new CoffeeOrder().setId(5));
        });
    }

    @Test
    void whenUpdatingUsingDifferentInstance_thenFindReturnsNewInstance() {
        CoffeeOrder coffeeOrder = new CoffeeOrder();
        int id = repository.create(coffeeOrder);
        CoffeeOrder newCoffeeOrder = new CoffeeOrder().setId(id);
        repository.update(newCoffeeOrder);

        Optional<CoffeeOrder> maybe = repository.findById(id);
        CoffeeOrder returned = maybe.get();
        Assertions.assertSame(returned, newCoffeeOrder);
    }
}
