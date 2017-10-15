package com.baeldung.kotlin.junit5

import org.junit.jupiter.api.*
import org.junit.jupiter.api.function.Executable

class CalculatorTest5 {
    private val calculator = Calculator()

    @Test
    fun testAddition() {
        Assertions.assertEquals(4, calculator.add(1, 3))
    }

    @Test
    fun testDivideByZero() {
        val exception = Assertions.assertThrows(DivideByZeroException::class.java) {
            calculator.divide(5, 0)
        }

        Assertions.assertEquals(5, exception.numerator)
    }

    @Test
    fun testSquares() {
        Assertions.assertAll(
                Executable { Assertions.assertEquals(1, calculator.square(1)) },
                Executable { Assertions.assertEquals(4, calculator.square(2)) },
                Executable { Assertions.assertEquals(9, calculator.square(3)) }
        )
    }

    @TestFactory
    fun testSquaresFactory() = listOf(
            DynamicTest.dynamicTest("1 squared") { Assertions.assertEquals(1,calculator.square(1))},
            DynamicTest.dynamicTest("2 squared") { Assertions.assertEquals(4,calculator.square(2))},
            DynamicTest.dynamicTest("3 squared") { Assertions.assertEquals(9,calculator.square(3))}
    )

    @TestFactory
    fun testSquaresFactory2() = listOf(
            1 to 1,
            2 to 4,
            3 to 9,
            4 to 16,
            5 to 25)
            .map { (input, expected) ->
                DynamicTest.dynamicTest("$input squared") {
                    Assertions.assertEquals(expected, calculator.square(input))
                }
            }

    private val squaresTestData = listOf(
            1 to 1,
            2 to 4,
            3 to 9,
            4 to 16,
            5 to 25)

    @TestFactory
    fun testSquaresFactory3() = squaresTestData
            .map { (input, expected) ->
                DynamicTest.dynamicTest("$input squared") {
                    Assertions.assertEquals(expected, calculator.square(input))
                }
            }
    @TestFactory
    fun testSquareRootsFactory3() = squaresTestData
            .map { (expected, input) ->
                DynamicTest.dynamicTest("Square root of $input") {
                    Assertions.assertEquals(expected.toDouble(), calculator.squareRoot(input))
                }
            }

    @Tags(
            Tag("slow"),
            Tag("logarithms")
    )
    @Test
    fun testLogarithms() {
        Assertions.assertEquals(3.0, calculator.log(2, 8))
    }
}
