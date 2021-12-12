package com.baeldung.randomgenerators;

import static org.assertj.core.api.Assertions.*;
import java.util.Random;

class OldRandomUnitTest {

    void given_when_then() {
        Random random = new Random();
        int number = random.nextInt(10);
        assertThat(number).isPositive().isLessThan(10);
    }

}
