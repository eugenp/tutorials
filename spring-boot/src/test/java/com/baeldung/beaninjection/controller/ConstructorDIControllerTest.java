package com.baeldung.beaninjection.controller;

import com.baeldung.beaninjection.Application;
import com.baeldung.beaninjection.model.Author;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by ggallo on 29/01/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ConstructorDIControllerTest {

    @Autowired
    private ConstructorDIController constructorDIController;

    @Test
    public void whenCallToConstructorDIController_thenReturnListOfAuthors() {
        List<Author> authorList = constructorDIController.getAuthors();

        assertTrue(authorList.size() == 2);
        assertTrue(authorList.get(0).getName().equals("Gorka"));
        assertTrue(authorList.get(1).getName().equals("Peter"));
    }

}
