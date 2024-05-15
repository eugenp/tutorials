package com.baeldung.factorygeneric;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

// Unit test for the NotifierFactory class.
public class FactoryGenericUnitTest {
    
    // Checks that when the factory is asked to provide a string notifier, the object is an actual string notifier.
    @Test
    public void givenStringNotifier_whenEquals_thenTrue() {
        NotifierFactory factory = new NotifierFactory();
        Notifier<String> stringNotifier = factory.getNotifier(String.class);

        assertNotNull(stringNotifier, "The object returned by the factory is null!");
        assertTrue(stringNotifier instanceof Notifier<?>, "The object returned by the factory is not a notifier!");
    }

    // Checks that when the factory is asked to provide a date notifier, the object is an actual date notifier.
    @Test
    public void givenDateNotifier_whenEquals_thenTrue() {
        NotifierFactory factory = new NotifierFactory();
        Notifier<Date> dateNotifier = factory.getNotifier(Date.class);

        assertNotNull(dateNotifier, "The object returned by the factory is null!");
        assertTrue(dateNotifier instanceof Notifier<?>, "The object returned by the factory is not a notifier!");
    }

    // Checks that when the factory is asked to provide both a date notifier and a string notifier, the objects returned are different.
    @Test
    public void givenDateNotifierAndStringNotifier_whenEquals_thenFalse() {
        NotifierFactory factory = new NotifierFactory();
        Notifier<String> stringNotifier = factory.getNotifier(String.class);
        Notifier<Date> dateNotifier = factory.getNotifier(Date.class);
        
        assertNotNull(stringNotifier, "The object returned by the factory is null!");
        assertNotNull(dateNotifier, "The object returned by the factory is null!");
        assertNotEquals(stringNotifier, dateNotifier, "The string notifier and date notifier objects returned by the factory are actually the same!");
    }

}
