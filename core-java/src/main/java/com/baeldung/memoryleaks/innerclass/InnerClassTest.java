package com.baeldung.memoryleaks.innerclass;

public class InnerClassTest {
	public static InnerClassWrapper.SimpleInnerClass getSimpleInnerClassObj() {
		return new InnerClassWrapper().new SimpleInnerClass();
	}
	
	public static void main2(String[] args) {
		InnerClassWrapper.SimpleInnerClass simpleInnerClassObj = getSimpleInnerClassObj();
		System.out.println("Debug point");
	}
	
	public static void main(String[] args) {
		StaticNestedClassWrapper.StaticNestedClass simpleInnerClassObj = new StaticNestedClassWrapper.StaticNestedClass();
		System.out.println("Debug point");
	}
}
