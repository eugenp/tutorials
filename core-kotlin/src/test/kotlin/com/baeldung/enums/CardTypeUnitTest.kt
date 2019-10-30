package com.baeldung.enums

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CardTypeUnitTest {

    @Test
    fun givenSilverCardType_whenCalculateCashbackPercent_thenReturnCashbackValue() {
        assertEquals(0.25f, CardType.SILVER.calculateCashbackPercent())
    }

    @Test
    fun givenGoldCardType_whenCalculateCashbackPercent_thenReturnCashbackValue() {
        assertEquals(0.5f, CardType.GOLD.calculateCashbackPercent())
    }

    @Test
    fun givenPlatinumCardType_whenCalculateCashbackPercent_thenReturnCashbackValue() {
        assertEquals(0.75f, CardType.PLATINUM.calculateCashbackPercent())
    }

    @Test
    fun givenSilverCardType_whenGetCreditLimit_thenReturnCreditLimit() {
        assertEquals(100000, CardType.SILVER.getCreditLimit())
    }

    @Test
    fun givenGoldCardType_whenGetCreditLimit_thenReturnCreditLimit() {
        assertEquals(200000, CardType.GOLD.getCreditLimit())
    }

    @Test
    fun givenPlatinumCardType_whenGetCreditLimit_thenReturnCreditLimit() {
        assertEquals(300000, CardType.PLATINUM.getCreditLimit())
    }

    @Test
    fun givenSilverCardType_whenCheckColor_thenReturnColor() {
        assertEquals("gray", CardType.SILVER.color)
    }

    @Test
    fun givenGoldCardType_whenCheckColor_thenReturnColor() {
        assertEquals("yellow", CardType.GOLD.color)
    }

    @Test
    fun givenPlatinumCardType_whenCheckColor_thenReturnColor() {
        assertEquals("black", CardType.PLATINUM.color)
    }

    @Test
    fun whenGetCardTypeByColor_thenSilverCardType() {
        Assertions.assertEquals(CardType.SILVER, CardType.getCardTypeByColor("gray"))
    }

    @Test
    fun whenGetCardTypeByColor_thenGoldCardType() {
        Assertions.assertEquals(CardType.GOLD, CardType.getCardTypeByColor("yellow"))
    }

    @Test
    fun whenGetCardTypeByColor_thenPlatinumCardType() {
        Assertions.assertEquals(CardType.PLATINUM, CardType.getCardTypeByColor("black"))
    }

    @Test
    fun whenGetCardTypeByName_thenSilverCardType() {
        Assertions.assertEquals(CardType.SILVER, CardType.getCardTypeByName("silver"))
    }

    @Test
    fun whenGetCardTypeByName_thenGoldCardType() {
        Assertions.assertEquals(CardType.GOLD, CardType.getCardTypeByName("gold"))
    }

    @Test
    fun whenGetCardTypeByName_thenPlatinumCardType() {
        Assertions.assertEquals(CardType.PLATINUM, CardType.getCardTypeByName("platinum"))
    }

}
