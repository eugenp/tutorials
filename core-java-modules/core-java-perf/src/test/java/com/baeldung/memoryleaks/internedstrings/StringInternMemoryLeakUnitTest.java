package com.baeldung.memoryleaks.internedstrings;

import org.junit.Ignore;
import org.junit.Test;

public class StringInternMemoryLeakUnitTest {
    @Test
    @Ignore // Test deliberately ignored as memory leak tests consume lots of resources
    public void givenJava6OrBelow_whenInterningLargeStrings_thenPermgenIncreases() {
        new InternedString().readString();
        System.out.print("Debug Point - VisuaLVM");
    }
    
    @Test
    @Ignore // Test deliberately ignored as memory leak tests consume lots of resources
    public void givenJava6OrBelow_whenNotInterningLargeStrings_thenPermgenDoesntIncrease() {
        new StringObject().readString();
        System.out.print("Debug Point - VisuaLVM");
    }
}
