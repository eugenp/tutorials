package com.baeldung.javafeatures;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.javafeatures.classfile.ClassFileDriver;

public class Java22ExamplesUnitTest {

    @Test
    public void givenJava22_whenUsingLanguagePatterns_thenReturnResult() {
        UnnamedExpressions um = new UnnamedExpressions();
        Assert.assertEquals(0, um.unnamedExpressionExampleUsingException(100));
        Assert.assertEquals(123, um.unnamedExpressionsExampleUsingSwitch(123));
        Assert.assertTrue(um.unnamedExpressionForTryWithResources());
        Assert.assertNotNull(um.unnamedExpressionForLambda());
    }

    @Test
    public void givenJava22_whenUsingClassFileAPI_thenReturnUpdatedClass() throws IOException {
        ClassFileDriver classFileDriver = new ClassFileDriver();
        Assert.assertNotNull(classFileDriver.updateClass());
    }

    @Test
    public void givenJava22_whenUsingForeignAPI_thenReturnLengthUsingCFunction() throws Throwable {
        MemoryAPIExample mp = new MemoryAPIExample();
        Assert.assertEquals(11, mp.getLengthUsingNativeMethod("Hello world"));
    }

    @Test
    public void givenJava22_whenUsingStreamGatherer_thenReturnCustomList() {
        Assert.assertNotNull(new StreamGatherersExample().gatherIntoWindows(List.of("India", "Poland", "UK", "Australia", "USA", "Netherlands")));
    }
}
