
package com.baeldung.java9.optional;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class OptionalsTest {

    @Test
    public static void givenCustomOptional_whenInvoke_thenOptional() {
        
    }
    
    @Test
    public static void givenGuavaOptional_whenInvoke_thenOptional() {
        String name = "Filan Fisteku";
        com.google.common.base.Optional<String> stringOptional =  com.google.common.base.Optional.of(name);
        assertEquals(name, Optionals.getName(stringOptional));
    }

}