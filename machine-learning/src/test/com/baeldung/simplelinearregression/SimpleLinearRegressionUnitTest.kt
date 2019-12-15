package com.baeldung.simplelinearregression

import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test

class SimpleLinearRegressionUnitTest {
    @Test
    fun givenAProperDataSetWhenFedToASimpleLinearRegressionModelThenItPredictsCorrectly() {
        val xs = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val ys = arrayListOf(25, 35, 49, 60, 75, 90, 115, 130, 150, 200)

        val model = SimpleLinearRegression(xs, ys)

        val predictionOne = model.predict(2.5)
        assertEquals(38.99, predictionOne, 0.01)

        val predictionTwo = model.predict(7.5)
        assertEquals(128.84, predictionTwo, 0.01)
    }

    @Test
    fun givenAPredictableDataSetWhenCalculatingTheLossFunctionThenTheModelIsConsideredReliable() {
        val xs = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val ys = arrayListOf(25, 35, 49, 60, 75, 90, 115, 130, 150, 200)

        val model = SimpleLinearRegression(xs, ys)

        assertEquals(0.95, model.calculateRSquared(), 0.01)
    }

    @Test
    fun givenAnUnpredictableDataSetWhenCalculatingTheLossFunctionThenTheModelIsConsideredUnreliable() {
        val xs = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val ys = arrayListOf(200, 0, 200, 0, 0, 0, -115, 1000, 0, 1)

        val model = SimpleLinearRegression(xs, ys)

        assertEquals(0.01, model.calculateRSquared(), 0.01)
    }
}