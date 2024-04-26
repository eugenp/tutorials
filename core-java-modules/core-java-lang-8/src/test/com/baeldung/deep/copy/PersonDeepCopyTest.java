package com.baeldung.deep.copy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.baeldung.Hobby;

public class PersonDeepCopyTest {

    @Test
    public void assertThatNewObjectCreated() {
        List<Hobby> hobbies = new ArrayList<>();
        hobbies.add(new Hobby("Tennis", "Play some tennis"));
        hobbies.add(new Hobby("Photography", "Photography is the art and practice of capturing images using a camera"));

        PersonDeepCopy p = new PersonDeepCopy("VladD", 32, hobbies);
        PersonDeepCopy copy = new PersonDeepCopy(p);

        assertEquals(2, copy.getHobbies()
            .size());
        p.getHobbies()
            .add(new Hobby("Gardening", "Gardening is the cultivation and nurturing of plants, often done for pleasure and relaxation"));

        assertEquals(2, copy.getHobbies()
            .size());
        assertEquals(3, p.getHobbies()
            .size());
    }

    @Test
    public void assertThatObjectModificationNotPropagateCreated() {
        List<Hobby> hobbies = new ArrayList<>();
        hobbies.add(new Hobby("Tennis", "Play some tennis"));
        hobbies.add(new Hobby("Photography", "Photography is the art and practice of capturing images using a camera"));

        PersonDeepCopy p = new PersonDeepCopy("VladD", 32, hobbies);
        PersonDeepCopy copy = new PersonDeepCopy(p);

        assertEquals(2, copy.getHobbies()
            .size());
        p.getHobbies()
            .getFirst()
            .setName("This is a Test");

        assertNotEquals(p.getHobbies(), copy.getHobbies());
    }
}
