package com.baeldung.kotlinvsjava

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class OperatorsOverloadingTest {

    @Test
    fun givenThePlaneClassWithOverloadedIncrementationOperator_whenCallingTheOperator_thenItIncreasesSpeed(){
        var plane = Plane(0.0)

        plane++
        assertEquals(50.0, plane.currentSpeed)
    }

    @Test
    fun givenThePlaneClassWithOverloadedMinusOperator_whenCallingTheOperator_thenItDecreaseSpeed(){
        var plane = Plane(1000.0)

        plane - 500.0
        assertEquals(500.0, plane.currentSpeed)
    }

    @Test
    fun givenThePlaneClassWithOverloadedInvokeOperator_whenCallingTheOperator_thenItSetSpeed(){
        var plane = Plane(0.0)

        plane(150.0)
        assertEquals(150.0, plane.currentSpeed)
    }

    @Test
    fun given2PlaneObjectWithOverloadedComparisonOperator_whenCallingTheOperator_thenItComparesSpeedValues(){
        var plane = Plane(0.0)
        var plane2 = Plane(150.0)

        assertTrue(plane < (plane2))
    }

}

class Plane(var currentSpeed: Double) {

    operator fun inc(): Plane {
        currentSpeed += 50.0
        return this
    }

    operator fun minus(number: Double) {
        currentSpeed = if(currentSpeed < number) 0.0 else currentSpeed - number
    }

    operator fun invoke(speed: Double) {
        currentSpeed = speed
    }

    operator fun compareTo(plane: Plane): Int {
        return currentSpeed.compareTo(plane.currentSpeed)
    }
}