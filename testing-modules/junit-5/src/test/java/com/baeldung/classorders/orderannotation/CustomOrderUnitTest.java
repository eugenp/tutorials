package com.baeldung.classorders.orderannotation;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestClassOrder;

import com.baeldung.classorders.CustomClassOrderer;
import com.baeldung.classorders.TestA;
import com.baeldung.classorders.TestB;
import com.baeldung.classorders.TestC;

@TestClassOrder(CustomClassOrderer.class)
public class CustomOrderUnitTest {
    @Nested
    class Longest extends TestA {}

    @Nested
    class Middle extends TestB {}

    @Nested
    class Short extends TestC {}
}