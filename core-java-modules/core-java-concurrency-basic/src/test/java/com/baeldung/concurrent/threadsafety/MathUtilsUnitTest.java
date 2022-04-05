package com.baeldung.concurrent.threadsafety;

import com.baeldung.concurrent.threadsafety.mathutils.MathUtils;
import org.junit.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class MathUtilsUnitTest {
    
    @Test
    public void whenCalledFactorialMethod_thenCorrect() {
        assertThat(MathUtils.factorial(2)).isEqualTo(new BigInteger("2"));
    }
}
