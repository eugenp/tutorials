package com.baeldung.staticvalueinjection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = StaticPropertyHolder.class)
public class StaticPropertyHolderUnitTest {

    @Test
    public void givenStaticPropertyInSpringBean_WhenUsingValueOnSetter_ThenValueInjected() {
        assertNull(StaticPropertyHolder.getStaticNameInjectedOnField());
        assertEquals("Inject a value to a static field", StaticPropertyHolder.getStaticName());
    }

}