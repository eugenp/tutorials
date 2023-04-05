package com.baeldung.deepandshallowcopy;

import com.baeldung.deepandshallowcopy.*;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class DeepAndShallowCopyUnitTest {
    @Test
    public void whenCreatingShallowCopy_thenObjectsShareSameFavoriteFood() {
        FavoriteFood favoriteFood = new FavoriteFood("Pizza");
        Person originalPerson = new Person("John", 30, favoriteFood);

        try {
            // Create a shallow copy of the originalPerson
            Person clonedPerson = (Person) originalPerson.clone();

            // Check if the cloned person's name and favorite food are the same as the original person
            assertThat(clonedPerson.name).isEqualTo("John");
            assertThat(clonedPerson.favoriteFood.food).isEqualTo("Pizza");
        } catch (CloneNotSupportedException e) {
            fail("Clone is not supported");
        }
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
