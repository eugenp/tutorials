package com.baeldung.recordproperties;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

record Player(String name, int age, Long score) {
}

public class ReadRecordPropertiesByReflectionUnitTest {
    private static final Player ERIC = new Player("Eric", 28, 4242L);

    @Test
    void whenUsingRecordComponent_thenGetExpectedResult() {
        var fields = new ArrayList<Field>();
        RecordComponent[] components = Player.class.getRecordComponents();
        for (var comp : components) {
            try {
                Field field = ERIC.getClass()
                  .getDeclaredField(comp.getName());
                field.setAccessible(true);
                fields.add(field);
            } catch (NoSuchFieldException e) {
                // for simplicity, error handling is skipped
            }
        }

        assertEquals(3, fields.size());

        var nameField = fields.get(0);
        var ageField = fields.get(1);
        var scoreField = fields.get(2);
        try {
            assertEquals("name", nameField.getName());
            assertEquals(String.class, nameField.getType());
            assertEquals("Eric", nameField.get(ERIC));

            assertEquals("age", ageField.getName());
            assertEquals(int.class, ageField.getType());
            assertEquals(28, ageField.get(ERIC));

            assertEquals("score", scoreField.getName());
            assertEquals(Long.class, scoreField.getType());
            assertEquals(4242L, scoreField.get(ERIC));
        } catch (IllegalAccessException exception) {
            // for simplicity, error handling is skipped
        }

    }

    @Test
    void whenUsingClassGetDeclaredField_thenGetExpectedResult() {
        // record has no public fields
        assertEquals(0, Player.class.getFields().length);

        var fields = new ArrayList<Field>();
        for (var field : Player.class.getDeclaredFields()) {
            field.setAccessible(true);
            fields.add(field);
        }

        assertEquals(3, fields.size());
        var nameField = fields.get(0);
        var ageField = fields.get(1);
        var scoreField = fields.get(2);

        try {
            assertEquals("name", nameField.getName());
            assertEquals(String.class, nameField.getType());
            assertEquals("Eric", nameField.get(ERIC));

            assertEquals("age", ageField.getName());
            assertEquals(int.class, ageField.getType());
            assertEquals(28, ageField.get(ERIC));

            assertEquals("score", scoreField.getName());
            assertEquals(Long.class, scoreField.getType());
            assertEquals(4242L, scoreField.get(ERIC));
        } catch (IllegalAccessException ex) {
            // for simplicity, error handling is skipped
        }

    }

}