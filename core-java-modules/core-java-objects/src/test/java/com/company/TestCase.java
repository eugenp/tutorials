package com.company;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestCase {
    @Test
    void testShallowCopyModifiesSharedData() {

        List<Person> johnFriends = new ArrayList<>(Arrays.asList(new Person("John", 35)));
        Person originalPerson = new Person("Peter", 37, johnFriends);

        Person shallowCopy = originalPerson;

        assertEquals(1, originalPerson.getFriends().size());
        shallowCopy.getFriends().add(new Person("Jane", 38));

        assertEquals(2, shallowCopy.getFriends().size());

        assertEquals(2, originalPerson.getFriends().size());
    }

    @Test
    void testDeepCopyDoesntShareMutableState() {

        List<Person> johnFriends = new ArrayList<>(Arrays.asList(new Person("John", 35)));
        Person originalPerson = new Person("Peter", 37, johnFriends);

        Person deepCopyOfPerson = originalPerson.deepCopy();

        assertEquals(1, originalPerson.getFriends().size());

        deepCopyOfPerson.getFriends().add(new Person("Jane", 38));

        assertEquals(2, deepCopyOfPerson.getFriends().size());

        assertEquals(1, originalPerson.getFriends().size());
    }

}
