package com.baeldung.construction;

import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.MockedConstruction;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import static org.mockito.Mockito.mockConstruction;

class FruitUnitTest {

    @Test
    void givenMockedContructor_whenFruitCreated_thenMockIsReturned() {
        assertEquals("Apple", new Fruit().getName());
        assertEquals("Red", new Fruit().getColour());

        try (MockedConstruction<Fruit> mock = mockConstruction(Fruit.class)) {

            Fruit fruit = new Fruit();
            when(fruit.getName()).thenReturn("Banana");
            when(fruit.getColour()).thenReturn("Yellow");

            assertEquals("Banana", fruit.getName());
            assertEquals("Yellow", fruit.getColour());

            List<Fruit> constructed = mock.constructed();
            assertEquals(1, constructed.size());
        }
    }
    
    @Test
    void givenMockedContructorWithNewDefaultAnswer_whenFruitCreated_thenRealMethodInvoked() {
        try (MockedConstruction<Fruit> mock = mockConstruction(Fruit.class, withSettings().defaultAnswer(Answers.CALLS_REAL_METHODS))) {

            Fruit fruit = new Fruit();

            assertEquals("Apple", fruit.getName());
            assertEquals("Red", fruit.getColour());

            List<Fruit> constructed = mock.constructed();
            assertEquals(1, constructed.size());
        }
    }

}
