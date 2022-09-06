package com.baeldung;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.performancemonitor.Person;
import com.baeldung.performancemonitor.PersonService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SpringContextTest {

    @Autowired
    private PersonService service;

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
        assertNotNull(service);
        Person person = new Person("John","Smith", LocalDate.of(1980, Month.JANUARY, 12));
        service.getFullName(person);
        service.getAge(person);
    }
}
