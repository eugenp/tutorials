package com.baeldung.enums

class CardTypeHelper {
    fun getCardTypeByColor(color: String): CardType? {
        for (cardType in CardType.values()) {
            if (cardType.color.equals(color)) {
                return cardType;
            }
        }
        return null
    }

    fun getCardTypeByName(name: String): CardType {
        return CardType.valueOf(name.toUpperCase())
    }
}