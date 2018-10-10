package com.baeldung.kotlin.spek

import com.baeldung.kotlin.junit5.Calculator
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.subject.SubjectSpek
import org.junit.jupiter.api.Assertions.assertEquals

class CalculatorSubjectTest5 : SubjectSpek<Calculator>({
    subject { Calculator() }
    describe("A calculator") {
        describe("Addition") {
            val result = subject.add(3, 5)
            it("Produces the correct answer") {
                assertEquals(8, result)
            }
        }
    }
})
