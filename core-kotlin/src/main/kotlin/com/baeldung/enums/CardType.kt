package com.baeldung.enums

enum class CardType(val color: String) : ICardLimit {
    SILVER("gray") {
        override fun getCreditLimit(): Int {
            return 100000
        }

        override fun calculateCashbackPercent(): Float {
            return 0.25f
        }
    },
    GOLD("yellow") {
        override fun getCreditLimit(): Int {
            return 200000
        }

        override fun calculateCashbackPercent(): Float {
            return 0.5f
        }
    },
    PLATINUM("black") {
        override fun getCreditLimit(): Int {
            return 300000
        }

        override fun calculateCashbackPercent(): Float {
            return 0.75f
        }
    };

    abstract fun calculateCashbackPercent(): Float
}