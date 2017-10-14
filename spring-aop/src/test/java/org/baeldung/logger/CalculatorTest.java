package org.baeldung.logger;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AdderConfig.class })
public class CalculatorTest {

    @Autowired
    private SampleAdder sampleAdder;

    @Test
    public void whenAddValidValues_returnsSucessfully() {
        final int addedValue = sampleAdder.add(12, 12);

        assertEquals(24, addedValue);
    }

}
