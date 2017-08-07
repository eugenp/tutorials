package org.baeldung.bean.injection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This is the JUnit Test class for Zoo class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ZooTest {

    @Autowired
    private Zoo zoo;

    @Test
    public void getAnimalOfZoo() {
        assert zoo.getAnimalOfZoo() != null;
    }
}
