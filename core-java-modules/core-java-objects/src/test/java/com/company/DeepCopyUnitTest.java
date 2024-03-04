package com.company;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DeepCopyUnitTest {

    @Test
    void whenCreatingDeepCopy_thenObjectsShouldNotBeSame() {

        List<Person> johnFriends = new ArrayList<>(Arrays.asList(new Person("John", 35)));
        Person originalPerson = new Person("Peter", 37, johnFriends);

        Person deepCopyOfPerson = originalPerson.deepCopy();

        assertNotEquals(originalPerson, deepCopyOfPerson);

    }

    @Test
    void whenModifiyingDeepCopy_thenOriginalShouldNotChange() {

        List<Person> johnFriends = new ArrayList<>(Arrays.asList(new Person("John", 35)));
        Person originalPerson = new Person("Peter", 37, johnFriends);

        Person deepCopyOfPerson = originalPerson.deepCopy();

        deepCopyOfPerson.getFriends()
            .add(new Person("Jane", 38));

        assertNotEquals(originalPerson.getFriends()
            .size(), deepCopyOfPerson.getFriends()
            .size());

    }

}
