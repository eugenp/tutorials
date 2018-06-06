package com.baeldung.enums

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class CardTypeHelperUnitTest {

    @Test
    fun whenGetCardTypeByColor_thenSilverCardType() {
        val cardTypeHelper = CardTypeHelper()
        Assertions.assertEquals(CardType.SILVER, cardTypeHelper.getCardTypeByColor("gray"))
    }

    @Test
    fun whenGetCardTypeByColor_thenGoldCardType() {
        val cardTypeHelper = CardTypeHelper()
        Assertions.assertEquals(CardType.GOLD, cardTypeHelper.getCardTypeByColor("yellow"))
    }

    @Test
    fun whenGetCardTypeByColor_thenPlatinumCardType() {
        val cardTypeHelper = CardTypeHelper()
        Assertions.assertEquals(CardType.PLATINUM, cardTypeHelper.getCardTypeByColor("black"))
    }

    @Test
    fun whenGetCardTypeByName_thenSilverCardType() {
        val cardTypeHelper = CardTypeHelper()
        Assertions.assertEquals(CardType.SILVER, cardTypeHelper.getCardTypeByName("silver"))
    }

    @Test
    fun whenGetCardTypeByName_thenGoldCardType() {
        val cardTypeHelper = CardTypeHelper()
        Assertions.assertEquals(CardType.GOLD, cardTypeHelper.getCardTypeByName("gold"))
    }

    @Test
    fun whenGetCardTypeByName_thenPlatinumCardType() {
        val cardTypeHelper = CardTypeHelper()
        Assertions.assertEquals(CardType.PLATINUM, cardTypeHelper.getCardTypeByName("platinum"))
    }
}
