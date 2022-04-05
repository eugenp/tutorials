package com.baeldung.autowiring.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FlawedControllerIntegrationTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FlawedControllerIntegrationTest.class);
    
    @Autowired
    FlawedController myController;

    @Test
    void whenControl_ThenThrowNullPointerException() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> myController.control());
        LOGGER.error("Got a NullPointerException", npe);
    }

}
