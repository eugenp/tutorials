package com.baeldung.jar;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class JarAppUnitTest {

    @Test
    public void findClassTest(){
        Pattern databindPattern = Pattern.compile(".*jackson-databind-(\\d)+\\.(\\d)+\\.(\\d)\\.jar$");

        Assert.assertTrue(databindPattern.matcher(JarApp.findObjectMapperClass()).matches());
    }
}
