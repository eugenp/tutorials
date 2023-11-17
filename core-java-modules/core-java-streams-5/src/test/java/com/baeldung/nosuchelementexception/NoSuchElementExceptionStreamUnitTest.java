package com.baeldung.nosuchelementexception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Test;

public class NoSuchElementExceptionStreamUnitTest {

    @Test(expected = NoSuchElementException.class)
    public void givenEmptyOptional_whenCallingGetMethod_thenThrowNoSuchElementException() {
        List<String> names = List.of("William", "Amelia", "Albert", "Philip");
        Optional<String> emptyOptional = names.stream()
          .filter(name -> name.equals("Emma"))
          .findFirst();

        emptyOptional.get();
    }

    @Test
    public void givenEmptyOptional_whenUsingIsPresentMethod_thenReturnDefault() {
        List<String> names = List.of("Tyler", "Amelia", "James", "Emma");
        Optional<String> emptyOptional = names.stream()
          .filter(name -> name.equals("Lucas"))
          .findFirst();

        String name = "unknown";
        if (emptyOptional.isPresent()) {
            name = emptyOptional.get();
        }

        assertEquals("unknown", name);
    }

    @Test
    public void givenEmptyOptional_whenUsingOrElseMethod_thenReturnDefault() {
        List<String> names = List.of("Nicholas", "Justin", "James");
        Optional<String> emptyOptional = names.stream()
          .filter(name -> name.equals("Lucas"))
          .findFirst();

        String name = emptyOptional.orElse("unknown");

        assertEquals("unknown", name);
    }

    @Test
    public void givenEmptyOptional_whenUsingOrElseGetMethod_thenReturnDefault() {
        List<String> names = List.of("Thomas", "Catherine", "David", "Olivia");
        Optional<String> emptyOptional = names.stream()
          .filter(name -> name.equals("Liam"))
          .findFirst();

        String name = emptyOptional.orElseGet(() -> "unknown");

        assertEquals("unknown", name);
    }

}
