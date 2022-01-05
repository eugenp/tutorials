package com.baeldung.jar;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class AppUnitTest {

    @Test
    public void findClassTest(){
        Assert.assertTrue(App.findObjectMapperClass().endsWith("jackson-databind-2.13.1.jar"));
    }
}
