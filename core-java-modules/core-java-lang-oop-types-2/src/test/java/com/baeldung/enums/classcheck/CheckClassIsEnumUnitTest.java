package com.baeldung.enums.classcheck;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

enum Device {
    Keyboard, Monitor, Mouse, Printer
}

enum Weekday {
    Monday, Tuesday, Wednesday, Thursday, Friday,
    Saturday {
        @Override
        boolean isWeekend() {
            return true;
        }
    },
    Sunday {
        @Override
        boolean isWeekend() {
            return true;
        }
    };

    boolean isWeekend() {
        return false;
    }
}

public class CheckClassIsEnumUnitTest {

    @Test
    void whenUsingInstanceOf_thenGetExpectedResult() {
        Object obj = Device.Keyboard;
        assertTrue(obj instanceof Enum);
    }

    @Test
    void whenUsingisInstance_thenGetExpectedResult() {
        Object obj = Device.Keyboard;
        assertTrue(Enum.class.isInstance(obj));
    }

    @Test
    void whenUsingEnumClassisAssignableFrom_thenGetExpectedResult() {
        Object obj = Device.Keyboard;
        assertTrue(Enum.class.isAssignableFrom(obj.getClass()));
    }

    @Test
    void whenUsingGetClassIsEnum_thenGetExpectedResult() {
        assertTrue(Device.class.isEnum());

        Object obj = Device.Keyboard;
        assertTrue(obj.getClass().isEnum());
    }


    @Test
    void whenEnum_thenGetExpectedResult() {
        Object monday = Weekday.Monday;
        assertTrue(monday instanceof Enum);
        assertTrue(Enum.class.isInstance(monday));
        assertTrue(Enum.class.isAssignableFrom(monday.getClass()));
        assertTrue(monday.getClass().isEnum());

        Object sunday = Weekday.Sunday;
        assertTrue(sunday instanceof Enum);
        assertTrue(Enum.class.isInstance(sunday));
        assertTrue(Enum.class.isAssignableFrom(sunday.getClass()));
        assertFalse(sunday.getClass().isEnum()); // <-- isEnum() check failed when Enum values with body
    }
}