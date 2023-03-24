package com.baeldung.monad;

import org.junit.Assert;
import org.junit.Test;

public class MonadSampleUnitTest {

    @Test
    public void whenNotUsingMonad_shouldBeOk() {
        MonadSample1 test = new MonadSample1();
        Assert.assertEquals(6.0, test.apply(2), 0.000);
    }

    @Test
    public void whenNotUsingMonadButUsingTempVars_shouldBeOk() {
        MonadSample2 test = new MonadSample2();
        Assert.assertEquals(6.0, test.apply(2), 0.000);
    }

    @Test
    public void whenUsingMonad_shouldBeOk() {
        MonadSample3 test = new MonadSample3();
        Assert.assertEquals(6.0, test.apply(2), 0.000);
    }

    @Test
    public void whenTestingMonadProperties_shouldBeOk() {
        MonadSample4 test = new MonadSample4();
        Assert.assertEquals(true, test.leftIdentity());
        Assert.assertEquals(true, test.rightIdentity());
        Assert.assertEquals(true, test.associativity());
    }

    @Test
    public void whenBreakingMonadProperties_shouldBeFalse() {
        MonadSample5 test = new MonadSample5();
        Assert.assertEquals(false, test.fail());
    }
}
