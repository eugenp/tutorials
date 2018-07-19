package com.baeldung.builder

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class BuilderPatternUnitTest {

    @Test
    fun whenBuildingFoodOrder_thenFieldsNotNull() {


        val foodOrder = FoodOrder.Builder()
                .bread("white bread")
                .meat("bacon")
                .fish("salmon")
                .condiments("olive oil")
                .build()

        Assertions.assertNotNull(foodOrder.bread)
        Assertions.assertNotNull(foodOrder.meat)
        Assertions.assertNotNull(foodOrder.condiments)
        Assertions.assertNotNull(foodOrder.fish)
    }
}