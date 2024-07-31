package com.baeldung.exceptions.nosuchmethoderror;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

class MainMenuUnitTest {

    @Test
    void whenGetSpecials_thenNotNull() {
        assertNotNull(MainMenu.getSpecials());
    }
}
