package com.baeldung.kotlin.spek

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.jupiter.api.Assertions

class DataDrivenTest5 : Spek({
    describe("A data driven test") {
        mapOf(
                "hello" to "HELLO",
                "world" to "WORLD"
        ).forEach { input, expected ->
            describe("Capitalising $input") {
                it("Correctly returns $expected") {
                    Assertions.assertEquals(expected, input.toUpperCase())
                }
            }
        }
    }
})
