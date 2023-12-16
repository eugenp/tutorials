package com.baeldung.copying.deep;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class DeepCopyingUnitTest {

    @Test
    void givenOriginalInstance_whenCloning_thenIndependentDeepCopy() {
        DeepCopying original = new DeepCopying();
        original.setName("Marcelo");
        original.setSubjects(new ArrayList<>(List.of("Maths", "Science")));
        try {
            DeepCopying deepCopy = (DeepCopying) original.clone();
            original.getSubjects().add("History");

            assertEquals(List.of("Maths", "Science", "History"), original.getSubjects());
            assertEquals(List.of("Maths", "Science"), deepCopy.getSubjects());
        } catch (CloneNotSupportedException e) {
            fail("Exception should not occur during cloning");
        }
    }

    @Test
    void givenOriginalInstance_whenChangingOriginal_thenDeepCopyRemainsUnaffected() {
        DeepCopying original = new DeepCopying();
        original.setName("Marcelo");
        original.setSubjects(new ArrayList<>(List.of("Maths", "Science")));
        try {
            DeepCopying deepCopy = (DeepCopying) original.clone();
            original.getSubjects().add("History");
            original.setName("New Name");
            assertNotEquals(original.getName(), deepCopy.getName());
            assertNotEquals(original.getSubjects(), deepCopy.getSubjects());
        } catch (CloneNotSupportedException e) {
            fail("Exception should not occur during cloning");
        }
    }

    @Test
    void givenOriginalInstance_whenCloning_thenSubjectsAreDifferentInstances() {
        DeepCopying original = new DeepCopying();
        original.setName("Marcelo");
        List<String> subjects = new ArrayList<>(List.of("Maths", "Science"));
        original.setSubjects(subjects);
        try {
            DeepCopying deepCopy = (DeepCopying) original.clone();
            assertNotSame(original.getSubjects(), deepCopy.getSubjects());
        } catch (CloneNotSupportedException e) {
            fail("Exception should not occur during cloning");
        }
    }

    @Test
    void givenOriginalInstance_whenCloning_thenSubjectsAreEqual() {
        DeepCopying original = new DeepCopying();
        List<String> subjects = new ArrayList<>(List.of("Maths", "Science"));
        original.setSubjects(subjects);
        try {
            DeepCopying deepCopy = (DeepCopying) original.clone();
            assertEquals(original.getSubjects(), deepCopy.getSubjects());
        } catch (CloneNotSupportedException e) {
            fail("Exception should not occur during cloning");
        }
    }
}


