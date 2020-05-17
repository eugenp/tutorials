package com.baeldung.staticmodifier;

import org.junit.Assert;
import org.junit.Test;

public class SingletonUnitTest {

    @Test
    public void givenStaticInnerClass_whenMultipleTimesInstanceCalled_thenOnlyOneTimeInitialized() {
        Singleton object1 = Singleton.getInstance();
        Singleton object2 = Singleton.getInstance();
        
        Assert.assertSame(object1, object2);
    }
}
