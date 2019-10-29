package com.baeldung.builder

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class BuilderPatternUnitTest {

    @Test
    fun whenBuildingFoodOrderSettingValues_thenFieldsNotNull() {

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

    @Test
    fun whenBuildingFoodOrderSettingValues_thenFieldsContainsValues() {

        val foodOrder = FoodOrder.Builder()
                .bread("white bread")
                .meat("bacon")
                .fish("salmon")
                .condiments("olive oil")
                .build()

        Assertions.assertEquals("white bread", foodOrder.bread)
        Assertions.assertEquals("bacon", foodOrder.meat)
        Assertions.assertEquals("olive oil", foodOrder.condiments)
        Assertions.assertEquals("salmon", foodOrder.fish)
    }

    @Test
    fun whenBuildingFoodOrderWithoutSettingValues_thenFieldsNull() {

        val foodOrder = FoodOrder.Builder()
                .build()

        Assertions.assertNull(foodOrder.bread)
        Assertions.assertNull(foodOrder.meat)
        Assertions.assertNull(foodOrder.condiments)
        Assertions.assertNull(foodOrder.fish)
    }


    @Test
    fun whenBuildingFoodOrderNamedSettingValues_thenFieldsNotNull() {

        val foodOrder = FoodOrderNamed(
                meat = "bacon",
                fish = "salmon",
                condiments = "olive oil",
                bread = "white bread"
        )

        Assertions.assertNotNull(foodOrder.bread)
        Assertions.assertNotNull(foodOrder.meat)
        Assertions.assertNotNull(foodOrder.condiments)
        Assertions.assertNotNull(foodOrder.fish)
    }

    @Test
    fun whenBuildingFoodOrderNamedSettingValues_thenFieldsContainsValues() {

        val foodOrder = FoodOrderNamed(
                meat = "bacon",
                fish = "salmon",
                condiments = "olive oil",
                bread = "white bread"
        )

        Assertions.assertEquals("white bread", foodOrder.bread)
        Assertions.assertEquals("bacon", foodOrder.meat)
        Assertions.assertEquals("olive oil", foodOrder.condiments)
        Assertions.assertEquals("salmon", foodOrder.fish)
    }

    @Test
    fun whenBuildingFoodOrderNamedWithoutSettingValues_thenFieldsNull() {

        val foodOrder = FoodOrderNamed()

        Assertions.assertNull(foodOrder.bread)
        Assertions.assertNull(foodOrder.meat)
        Assertions.assertNull(foodOrder.condiments)
        Assertions.assertNull(foodOrder.fish)
    }


    @Test
    fun whenBuildingFoodOrderApplySettingValues_thenFieldsNotNull() {

        val foodOrder = FoodOrderApply().apply {
            meat = "bacon"
            fish = "salmon"
            condiments = "olive oil"
            bread = "white bread"
        }

        Assertions.assertNotNull(foodOrder.bread)
        Assertions.assertNotNull(foodOrder.meat)
        Assertions.assertNotNull(foodOrder.condiments)
        Assertions.assertNotNull(foodOrder.fish)
    }

    @Test
    fun whenBuildingFoodOrderApplySettingValues_thenFieldsContainsValues() {

        val foodOrder = FoodOrderApply().apply {
            meat = "bacon"
            fish = "salmon"
            condiments = "olive oil"
            bread = "white bread"
        }

        Assertions.assertEquals("white bread", foodOrder.bread)
        Assertions.assertEquals("bacon", foodOrder.meat)
        Assertions.assertEquals("olive oil", foodOrder.condiments)
        Assertions.assertEquals("salmon", foodOrder.fish)
    }

    @Test
    fun whenBuildingFoodOrderApplyWithoutSettingValues_thenFieldsNull() {

        val foodOrder = FoodOrderApply()

        Assertions.assertNull(foodOrder.bread)
        Assertions.assertNull(foodOrder.meat)
        Assertions.assertNull(foodOrder.condiments)
        Assertions.assertNull(foodOrder.fish)
    }

}