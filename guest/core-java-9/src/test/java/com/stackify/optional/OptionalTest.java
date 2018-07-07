package com.stackify.optional;

import org.junit.Test;
import java.util.Optional;
import java.util.List;

import static org.junit.Assert.*;
import java.util.stream.Collectors;

public class OptionalTest {
    
    private User user;
    
    @Test
    public void whenEmptyOptional_thenGetValueFromOr() {
        User result = Optional.ofNullable(user)
                .or( () -> Optional.of(new User("default","1234"))).get();
                 
        assertEquals(result.getEmail(), "default");
    }
    
    @Test
    public void whenIfPresentOrElse_thenOk() {
        Optional.ofNullable(user)
              .ifPresentOrElse( u -> System.out.println("User is:" + u.getEmail()), () -> System.out.println("User not found"));
    }

    @Test
    public void whenGetStream_thenOk() {
        User user = new User("john@gmail.com", "1234");
        List<String> emails = Optional.ofNullable(user)
                .stream()
                .filter(u -> u.getEmail() != null && u.getEmail().contains("@"))
                .map( u -> u.getEmail())
                .collect(Collectors.toList());
   
        assertTrue(emails.size() == 1);
        assertEquals(emails.get(0), user.getEmail());
    }

}
