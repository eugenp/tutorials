package com.baeldung.beaninjectiontypes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LanguageService.class)
public class FieldInjectionIntegrationTest {

    @Autowired
    private IProgrammingLanguage favoriteLanguage;

    @Test
    public void givenAutowired_WhenSetOnField_ThenDependencyResolved() {
        assertNotNull(favoriteLanguage);
        assertEquals("Java", favoriteLanguage.getName());
    }
}
