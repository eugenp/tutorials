package com.baeldung.copying.shallow;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class ShallowCopyingDemoUnitTest {

    @Test
    void givenOriginalInstance_whenCloning_thenShallowCopy() {
        ShallowCopy original = new ShallowCopy();
        original.setName("Marcelo");
        original.setSubjects(new ArrayList<>(List.of("Physics", "Chemistry")));
        try {
            ShallowCopy shallowCopy = (ShallowCopy) original.clone();
            original.getSubjects().add("Biology");
            assertEquals(List.of("Physics", "Chemistry", "Biology"), original.getSubjects());
            assertEquals(List.of("Physics", "Chemistry", "Biology"), shallowCopy.getSubjects());
        } catch (CloneNotSupportedException e) {
            fail("Exception should not occur during cloning");
        }
    }
    @Test
    void givenOriginalInstance_whenCloning_thenIndependentShallowCopy() {
        ShallowCopy original = new ShallowCopy();
        original.setName("Marcelo");
        original.setSubjects(new ArrayList<>(List.of("Physics", "Chemistry")));
        try {
            ShallowCopy shallowCopy = (ShallowCopy) original.clone();
            original.setSubjects(new ArrayList<>(List.of("Biology")));
            assertNotEquals(original.getSubjects(), shallowCopy.getSubjects());
        } catch (CloneNotSupportedException e) {
            fail("Exception should not occur during cloning");
        }
    }

    @Test
    void givenEmptySubjectList_whenCloning_thenShallowCopy() {
        ShallowCopy original = new ShallowCopy();
        original.setName("Marcelo");
        original.setSubjects(new ArrayList<>());
        try {
            ShallowCopy shallowCopy = (ShallowCopy) original.clone();
            original.getSubjects().add("Biology");
            assertEquals(List.of("Biology"), shallowCopy.getSubjects());
        } catch (CloneNotSupportedException e) {
            fail("Exception should not occur during cloning");
        }
    }
}
