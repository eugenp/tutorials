package com.baeldung.guava.tutorial;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import org.junit.Assert;
import org.junit.Test;

public class InternBuilderUnitTest {

    @Test
    public void interBuilderTest() {

        Interner<Integer> interners = Interners.<Integer> newBuilder()
          .concurrencyLevel(2)
          .strong().<Integer> build();

        Assert.assertNotNull(interners);
    }

}
