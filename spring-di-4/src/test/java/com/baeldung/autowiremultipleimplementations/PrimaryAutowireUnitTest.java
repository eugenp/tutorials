package com.baeldung.autowiremultipleimplementations;

import com.baeldung.autowiremultipleimplementations.components.PrimaryAutowire;
import com.baeldung.autowiremultipleimplementations.candidates.GoodService;
import com.baeldung.autowiremultipleimplementations.candidates.GoodServiceA;
import com.baeldung.autowiremultipleimplementations.candidates.GoodServiceB;
import com.baeldung.autowiremultipleimplementations.candidates.GoodServiceC;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {PrimaryAutowire.class, GoodServiceC.class, GoodServiceB.class, GoodServiceA.class})
@ExtendWith(SpringExtension.class)
public class PrimaryAutowireUnitTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private PrimaryAutowire primaryAutowire;

    @Test
    public void whenPrimaryServiceInjected_thenItShouldBeGoodServiceC() {
        GoodService injectedService = context.getBean(GoodService.class);

        assertInstanceOf(GoodServiceC.class, injectedService);

        String expectedMessage = "Hello from C!"; // GoodServiceC returns this message
        String actualMessage = primaryAutowire.hello();

        assertEquals(expectedMessage, actualMessage);
    }
}
