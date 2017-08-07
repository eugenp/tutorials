package org.baeldung.bean.injection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This is the JUnit Test class for Forest class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ForestTest {
    
    @Autowired
    Forest forest;

    @Test
    public void checkAnimalInjection() {
        assert forest.getAnimal() != null;
    }

}
