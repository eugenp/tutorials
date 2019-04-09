package com.baeldung.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.entity.Fruit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FruitPopulatorTest {

    @Autowired
    private FruitRepository fruitRepository;

    @Test
    public void givenFruitJsonPopulatorThenShouldInsertRecordOnStart() {

        List<Fruit> fruits = fruitRepository.findAll();
        assertEquals("record count is not matching", 3, fruits.size());

        fruits.forEach(fruit -> {
            if (1 == fruit.getId()) {
                assertEquals("This is not an apple", "apple", fruit.getName());
                assertEquals("It is not a red colored fruit", "red", fruit.getColor());
            } else if (2 == fruit.getId()) {
                assertEquals("This is not a guava", "guava", fruit.getName());
                assertEquals("It is not a green colored fruit", "green", fruit.getColor());
            }
        });
    }

    @Test
    public void givenFruitXmlPopulatorThenShouldInsertRecordOnStart() {

        List<Fruit> fruits = fruitRepository.findAll();
        assertEquals("record count is not matching", 3, fruits.size());

        fruits.forEach(fruit -> {
            if (3 == fruit.getId()) {
                assertEquals("This is not a mango", "mango", fruit.getName());
                assertEquals("It is not a yellow colored fruit", "yellow", fruit.getColor());
            }
        });
    }

}
