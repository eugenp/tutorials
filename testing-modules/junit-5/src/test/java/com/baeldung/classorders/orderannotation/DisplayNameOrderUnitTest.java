package com.baeldung.classorders.orderannotation;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestClassOrder;

import com.baeldung.classorders.TestA;
import com.baeldung.classorders.TestB;
import com.baeldung.classorders.TestC;

@TestClassOrder(ClassOrderer.DisplayName.class)
public class DisplayNameOrderUnitTest {
    @Nested
    @DisplayName("Class C")
    class Z extends TestC {}

    @Nested
    @DisplayName("Class B")
    class A extends TestA {}

    @Nested
    @DisplayName("Class A")
    class B extends TestB {}
}