package com.baeldung.deepandshallowcopy;

import com.baeldung.deepandshallowcopy.*;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeepAndShallowCopyUnitTest {
    @Test
    public void whenCreatingShallowCopy_thenObjectsShareSameFavoriteFood() {
        FavoriteFood favoriteFood = new FavoriteFood("Pizza");
        Person originalPerson = new Person("John", 30, favoriteFood);

        // Shallow copy
        Person shallowCopyPerson = new Person(originalPerson.name, originalPerson.age, originalPerson.favoriteFood);

        // Modify the favorite food of the original person
        originalPerson.favoriteFood.food = "Burger";

        // Shallow copy should reflect the change
        assertThat(shallowCopyPerson.favoriteFood.food).isEqualTo("Burger");
    }

    @Test
    public void whenCreatingDeepCopyWithDeepCopyMethod_thenObjectsShouldNotShareSameFavoriteFood() {
        FavoriteFood favoriteFood = new FavoriteFood("Pizza");
        Person originalPerson = new Person("John", 30, favoriteFood);

        // Deep copy
        Person deepCopyPerson = originalPerson.deepCopy();

        // Modify the favorite food of the original person
        originalPerson.favoriteFood.food = "Burger";

        // Deep copy should remain unchanged
        assertThat(deepCopyPerson.favoriteFood.food).isEqualTo("Pizza");
    }
}

