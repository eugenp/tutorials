package com.baeldung.classorders.orderannotation;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestClassOrder;

import com.baeldung.classorders.TestA;
import com.baeldung.classorders.TestB;
import com.baeldung.classorders.TestC;

@TestClassOrder(ClassOrderer.ClassName.class)
public class ClassNameOrderUnitTest {
    @Nested
    class C extends TestC {}

    @Nested
    class B extends TestB {}

    @Nested
    class A extends TestA {}
}
