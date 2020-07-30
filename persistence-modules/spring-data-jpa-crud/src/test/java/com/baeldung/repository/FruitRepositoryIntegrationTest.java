package com.baeldung.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.entity.Fruit;

@RunWith(SpringRunner.class)
@SpringBootTest
class FruitRepositoryIntegrationTest {

    @Autowired
    private FruitRepository fruitRepository;

    @Transactional
    @Test
    @Sql(scripts = { "/test-fruit-data.sql" })
    public void givenFruits_WhenDeletedByColor_ThenDeletedFruitsShouldReturn() {

        List<Fruit> fruits = fruitRepository.deleteByColor("green");

        assertEquals("number of fruits are not matching", 2, fruits.size());
        fruits.forEach(fruit -> assertEquals("Its not a green fruit", "green", fruit.getColor()));
    }

    @Transactional
    @Test
    @Sql(scripts = { "/test-fruit-data.sql" })
    public void givenFruits_WhenDeletedByName_ThenDeletedFruitCountShouldReturn() {

        Long deletedFruitCount = fruitRepository.deleteByName("apple");

        assertEquals("deleted fruit count is not matching", 1, deletedFruitCount.intValue());
    }

    @Transactional
    @Test
    @Sql(scripts = { "/test-fruit-data.sql" })
    public void givenFruits_WhenRemovedByColor_ThenDeletedFruitsShouldReturn() {

        List<Fruit> fruits = fruitRepository.removeByColor("green");

        assertEquals("number of fruits are not matching", 2, fruits.size());
        fruits.forEach(fruit -> assertEquals("Its not a green fruit", "green", fruit.getColor()));
    }

    @Transactional
    @Test
    @Sql(scripts = { "/test-fruit-data.sql" })
    public void givenFruits_WhenRemovedByName_ThenDeletedFruitCountShouldReturn() {

        Long deletedFruitCount = fruitRepository.removeByName("apple");

        assertEquals("deleted fruit count is not matching", 1, deletedFruitCount.intValue());
    }

    @Transactional
    @Test
    @Sql(scripts = { "/test-fruit-data.sql" })
    public void givenFruits_WhenDeletedByColorOrName_ThenDeletedFruitsShouldReturn() {

        int deletedCount = fruitRepository.deleteFruits("apple", "green");

        assertEquals("number of fruits are not matching", 3, deletedCount);
    }
}