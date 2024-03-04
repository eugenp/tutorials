package com.company;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShallowCopyUnitTest {

    @Test
    void whenCreatingShallowCopy_thenObjectsShouldNotBeSame() {

        List<Person> johnFriends = new ArrayList<>(Arrays.asList(new Person("John", 35)));
        Person originalPerson = new Person("Peter", 37, johnFriends);

        Person shallowCopy = (Person) originalPerson.clone();

        assertNotEquals(originalPerson, shallowCopy);

    }

    @Test
    void whenModifiyingShallowCopy_thenOriginalShouldChange() {

        List<Person> johnFriends = new ArrayList<>(Arrays.asList(new Person("John", 35)));
        Person originalPerson = new Person("Peter", 37, johnFriends);

        Person shallowCopy = (Person) originalPerson.clone();

        shallowCopy.getFriends()
            .add(new Person("Jane", 38));

        assertEquals(originalPerson.getFriends()
            .size(), shallowCopy.getFriends()
            .size());

    }

}
