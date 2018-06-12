package com.baeldung.enums

enum class CardType(val color: String) : ICardLimit {
    SILVER("gray") {
        override fun getCreditLimit() = 100000
        override fun calculateCashbackPercent() = 0.25f
    },
    GOLD("yellow") {
        override fun getCreditLimit() = 200000
        override fun calculateCashbackPercent(): Float = 0.5f
    },
    PLATINUM("black") {
        override fun getCreditLimit() = 300000
        override fun calculateCashbackPercent() = 0.75f
    };

    companion object {
        fun getCardTypeByColor(color: String) = values().firstOrNull { it.color == color }
        fun getCardTypeByName(name: String) = valueOf(name.toUpperCase())
    }

    abstract fun calculateCashbackPercent(): Float
}