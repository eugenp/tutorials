package com.baeldung.operators

import java.math.BigDecimal

enum class Currency {
    DOLLARS, EURO
}

class Money(val amount: BigDecimal, val currency: Currency) : Comparable<Money> {

    override fun compareTo(other: Money): Int =
            convert(Currency.DOLLARS).compareTo(other.convert(Currency.DOLLARS))

    fun convert(currency: Currency): BigDecimal = TODO()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Money) return false

        if (amount != other.amount) return false
        if (currency != other.currency) return false

        return true
    }

    override fun hashCode(): Int {
        var result = amount.hashCode()
        result = 31 * result + currency.hashCode()
        return result
    }
}