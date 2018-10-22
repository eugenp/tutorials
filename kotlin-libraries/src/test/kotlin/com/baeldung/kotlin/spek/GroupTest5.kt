package com.baeldung.kotlin.spek

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

class GroupTest5 : Spek({
    describe("Outer group") {
        beforeEachTest {
            System.out.println("BeforeEachTest 0")
        }
        beforeGroup {
            System.out.println("BeforeGroup 0")
        }
        afterEachTest {
            System.out.println("AfterEachTest 0")
        }
        afterGroup {
            System.out.println("AfterGroup 0")
        }
        describe("Inner group 1") {
            beforeEachTest {
                System.out.println("BeforeEachTest 1")
            }
            beforeGroup {
                System.out.println("BeforeGroup 1")
            }
            afterEachTest {
                System.out.println("AfterEachTest 1")
            }
            afterGroup {
                System.out.println("AfterGroup 1")
            }
            it("Test 1") {
                System.out.println("Test 1")
            }
            it("Test 2") {
                System.out.println("Test 2")
            }
        }
        describe("Inner group 2") {
            beforeEachTest {
                System.out.println("BeforeEachTest 2")
            }
            beforeGroup {
                System.out.println("BeforeGroup 2")
            }
            afterEachTest {
                System.out.println("AfterEachTest 2")
            }
            afterGroup {
                System.out.println("AfterGroup 2")
            }
            it("Test 3") {
                System.out.println("Test 3")
            }
            it("Test 4") {
                System.out.println("Test 4")
            }
        }
    }
})
