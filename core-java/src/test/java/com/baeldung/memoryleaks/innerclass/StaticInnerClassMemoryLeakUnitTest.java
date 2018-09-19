package com.baeldung.memoryleaks.innerclass;

import org.junit.Test;

public class StaticInnerClassMemoryLeakUnitTest {
	@Test
	public void givenUsingInnerClass_whenInitializingInnerClass_thenInnerClassHoldsReferenceOfOuterObject() {
		InnerClassWrapper.SimpleInnerClass simpleInnerClassObj = new InnerClassWrapper().new SimpleInnerClass();
		System.out.print("Debug Point - VisuaLVM");
	}
	
	@Test
	public void givenUsingStaticNestedClass_whenInitializingInnerClass_thenStaticNestedClassDoesntReferenceOuterObject() {
		StaticNestedClassWrapper.StaticNestedClass staticNestedClassObj = new StaticNestedClassWrapper.StaticNestedClass();
		System.out.print("Debug Point - VisuaLVM");
	}
}
