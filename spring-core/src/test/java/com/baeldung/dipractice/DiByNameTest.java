package com.baeldung.dipractice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = App.class)
public class DiByNameTest {

    @Inject
    @Named("unit")
    private Unit unit;

    @Test
    public void givenInjectQualifier_WhenSetOnField_ThenDependencyValid() {
        assertNotNull(unit);
        assertEquals("Pass", "NeoTech".toUpperCase(), unit.getDetails()
            .toUpperCase());
    }
}
