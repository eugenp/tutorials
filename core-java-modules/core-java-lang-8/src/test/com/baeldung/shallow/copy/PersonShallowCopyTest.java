package com.baeldung.shallow.copy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.baeldung.Hobby;

public class PersonShallowCopyTest {

    @Test
    public void assertThatChangesAreReflected() {
        List<Hobby> hobbies = new ArrayList<>();
        hobbies.add(new Hobby("Tennis", "Play some tennis"));
        hobbies.add(new Hobby("Photography", "Photography is the art and practice of capturing images using a camera"));

        PersonShallowCopy p = new PersonShallowCopy("VladD", 32, hobbies);
        PersonShallowCopy copy = new PersonShallowCopy(p);
        assertEquals(2, copy.getHobbies()
            .size());

        p.getHobbies()
            .add(new Hobby("Gardening", "Gardening is the cultivation and nurturing of plants, often done for pleasure and relaxation"));

        assertEquals(3, copy.getHobbies()
            .size());
    }
}