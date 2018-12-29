package com.baeldung.memoryleaks.innerclass;

import org.junit.Ignore;
import org.junit.Test;

public class StaticInnerClassMemoryLeakUnitTest {
    @Test
    @Ignore // Test deliberately ignored as memory leak tests consume lots of resources
    public void givenUsingInnerClass_whenInitializingInnerClass_thenInnerClassHoldsReferenceOfOuterObject() {
        InnerClassWrapper.SimpleInnerClass simpleInnerClassObj = new InnerClassWrapper().new SimpleInnerClass();
        System.out.print("Debug Point - VisuaLVM");
    }
    
    @Test
    @Ignore // Test deliberately ignored as memory leak tests consume lots of resources
    public void givenUsingStaticNestedClass_whenInitializingInnerClass_thenStaticNestedClassDoesntReferenceOuterObject() {
        StaticNestedClassWrapper.StaticNestedClass staticNestedClassObj = new StaticNestedClassWrapper.StaticNestedClass();
        System.out.print("Debug Point - VisuaLVM");
    }
}
