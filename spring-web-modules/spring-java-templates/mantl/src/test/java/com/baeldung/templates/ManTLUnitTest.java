package com.baeldung.templates;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ManTLUnitTest {
    @Test
    void whenRenderingManTL_thenTheOutputIsCorrect() {
        String output = templates.mantl.ManTLDemo.render(new ManTLModel("Baeldung"));

        assertEquals("""
            
            Hello Baeldung!
            """, output);
    }

}
