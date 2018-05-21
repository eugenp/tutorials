package com.baeldung.daopattern.test;

import com.baeldung.daopattern.entities.User;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserUnitTest {
    
    private static User user;
    
    
    @BeforeClass
    public static void setUpUserInstance() {
        user = new User("John", "john@domain.com");
    }
    
    @Test
    public void givenUserInstance_whenCalledsetName_thenOneAssertion() {
        user.setName("Jake");
        assertThat(user.getName()).isEqualTo("Jake");
    }
    
    @Test
    public void givenUserInstance_whenCalledsetEmail_thenOneAssertion() {
        user.setEmail("jake@domain.com");
        assertThat(user.getEmail()).isEqualTo("jake@domain.com");
    }
    
    @Test
    public void givenUserInstance_whenCalledgetName_thenOneAssertion() {
        assertThat(user.getName()).isEqualTo("Jake");
    }
    
    @Test
    public void givenUserInstance_whenCalledgetEmail_thenOneAssertion() {
        assertThat(user.getEmail()).isEqualTo("jake@domain.com");
    }
}
