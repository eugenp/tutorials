package com.baeldung.egoebelbecker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = NamedPetConfiguration.class)
public class TestSetByName {

    @Autowired
    private Pet tarantula;

    @Test
    public void whenSettingByName_ThenCorrectPet() {
        assertTrue(tarantula.speciesByName()
            .equals("tarantula"));
    }

}
