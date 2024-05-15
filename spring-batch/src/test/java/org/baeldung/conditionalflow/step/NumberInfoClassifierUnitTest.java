package org.baeldung.conditionalflow.step;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.baeldung.conditionalflow.model.NumberInfo;
import org.junit.jupiter.api.Test;

class NumberInfoClassifierUnitTest {

    @Test
    void givenNumberInfo_whenProcess_thenConvertsToInteger() throws Exception {
        NumberInfoClassifier nic = new NumberInfoClassifier();
        assertEquals(Integer.valueOf(4), nic.process(NumberInfo.from(4)));
        assertEquals(Integer.valueOf(-4), nic.process(NumberInfo.from(-4)));
    }
}