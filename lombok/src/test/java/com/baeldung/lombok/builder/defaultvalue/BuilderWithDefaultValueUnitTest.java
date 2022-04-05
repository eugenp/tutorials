package com.baeldung.lombok.builder.defaultvalue;

import org.junit.Assert;
import org.junit.Test;

public class BuilderWithDefaultValueUnitTest {

    @Test
    public void givenBuilderWithDefaultValue_ThanDefaultValueIsPresent() {
        Pojo build = new Pojo().toBuilder()
            .build();
        Assert.assertEquals("foo", build.getName());
        Assert.assertTrue(build.isOriginal());
    }

    @Test
    public void givenBuilderWithDefaultValue_NoArgsWorksAlso() {
        Pojo build = new Pojo().toBuilder()
            .build();
        Pojo pojo = new Pojo();
        Assert.assertEquals(build.getName(), pojo.getName());
        Assert.assertTrue(build.isOriginal() == pojo.isOriginal());
    }

}
