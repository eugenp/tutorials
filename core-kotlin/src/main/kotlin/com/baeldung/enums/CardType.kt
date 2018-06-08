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

    companion object {
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

    abstract fun calculateCashbackPercent(): Float
}