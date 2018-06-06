package com.baeldung.kotlin.spek

import com.baeldung.kotlin.junit5.Calculator
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.jupiter.api.Assertions.assertEquals

class CalculatorTest5 : Spek({
    given("A calculator") {
        val calculator = Calculator()
        on("Adding 3 and 5") {
            val result = calculator.add(3, 5)
            it("Produces 8") {
                assertEquals(8, result)
            }
        }
    }

    describe("A calculator") {
        val calculator = Calculator()
        describe("Addition") {
            val result = calculator.add(3, 5)
            it("Produces the correct answer") {
                assertEquals(8, result)
            }
        }
    }

})
