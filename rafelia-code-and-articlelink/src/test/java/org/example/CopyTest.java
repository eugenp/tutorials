package org.example;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CopyTest {

    @Test
    public void testShallowCopy() {
        Food food = new Food("Pizza");
        Person original = new Person("John", food);
        Person shallowCopy = new Person(original);

        assertEquals(original.name, shallowCopy.name);
        assertEquals(original.food, shallowCopy.food);

        // Modify the food name
        shallowCopy.food.fav = "Pasta";

        // Both should reflect the change
        assertEquals("Pasta", original.food.fav);
        assertEquals("Pasta", shallowCopy.food.fav);
    }

    @Test
    public void testDeepCopy() {
        Food food = new Food("Pizza");
        Person original = new Person("John", food);
        Person deepCopy = original.deepCopy(original);

        assertEquals(original.name, deepCopy.name);
        assertNotSame(original.food, deepCopy.food);

        // Modify the food name
        deepCopy.food.fav = "Pasta";

        // Only the deep copy should reflect the change
        assertEquals("Pizza", original.food.fav);
        assertEquals("Pasta", deepCopy.food.fav);
    }
}
