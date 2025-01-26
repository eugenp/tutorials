package com.baeldung.classorders.orderannotation;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestClassOrder;

import com.baeldung.classorders.TestA;
import com.baeldung.classorders.TestB;
import com.baeldung.classorders.TestC;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class OrderAnnotationUnitTest {
    @Nested
    @Order(3)
    class A extends TestA {}

    @Nested
    @Order(1)
    class B extends TestB {}

    @Nested
    @Order(2)
    class C extends TestC {}
}