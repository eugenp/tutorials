package com.baledung.billpugh;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LazyLoadedSingletonUnitTest {

    @Test
    void givenLazyLoadedImpl_whenCallGetInstance_thenReturnSingleInstance() throws ClassNotFoundException {
        Class bs = Class.forName("com.baledung.billpugh.LazyLoadedSingleton");
        assertThrows(IllegalAccessException.class, () -> bs.getDeclaredConstructor().newInstance());

        LazyLoadedSingleton lazyLoadedSingletonObj1 = LazyLoadedSingleton.getInstance();
        LazyLoadedSingleton lazyLoadedSingletonObj2 = LazyLoadedSingleton.getInstance();
        assertEquals(lazyLoadedSingletonObj1.hashCode(), lazyLoadedSingletonObj2.hashCode());
    }
}
