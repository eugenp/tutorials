package com.baeldung.egoebelbecker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/TestConstructorInjection-context.xml" })
public class TestConstructorInjection {

    @Autowired
    private Family family;

    @Test
    public void whenConstructor_ThenCorrectPet() {
        assertTrue(family.getPetId()
            .equals("cat"));
    }

}
