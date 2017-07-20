package com.baeldung.egoebelbecker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class,
                      classes=DogConfiguration.class)
public class TestSetByType {

    @Autowired
    private Pet dog;

    @Test
    public void whenSettingByType_ThenCorrectPet() {
        assertTrue(dog.speciesByName()
            .equals("dog"));
    }
}
