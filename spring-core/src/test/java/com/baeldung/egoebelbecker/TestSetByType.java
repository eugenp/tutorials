package com.baeldung.egoebelbecker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/TestSetByType-context.xml" })
public class TestSetByType {

    @Autowired
    private Family family;

    @Test
    public void whenSettingByType_ThenCorrectPet() {
        assertTrue(family.getPetId()
            .equals("dog"));
    }
}
