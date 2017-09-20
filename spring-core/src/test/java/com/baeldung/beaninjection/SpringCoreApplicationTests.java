package com.baeldung.beaninjection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringCoreApplicationTests {

    @Autowired
    private Library library;
    @Autowired
    private Car car;

    @Test
    public void testSetterInjection() {
        Librarian expected = new Librarian("sam");
        Assert.assertEquals(expected, library.getLibrarian());
    }

    @Test
    public void testConstructorInjection() {
        Transmission transmission = new Transmission("step");
        Engine engine = new Engine("2L", 6);

        Assert.assertEquals(transmission, car.getTransmission());
        Assert.assertEquals(engine, car.getEngine());
    }

}
