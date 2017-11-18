package com.baeldung.ditypes;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RegisterarConfig.class)
public class RegisterarTest {

    @Autowired
    private Registrar reg;

    @Autowired
    private User user;

    @Test
    public void givenAutowiredAnnotation_whenSetOnField_thenRegistrarIsInjected() {
        assertNotNull(reg);
    }

    @Test
    public void givenAutowiredAnnotation_whenSetOnField_thenUserIsInjected() {
        assertEquals("User1: Alice is registered", user.register());
    }
}
