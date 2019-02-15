package com.baeldung.operators

import java.math.BigInteger

operator fun <T> MutableCollection<T>.plusAssign(element: T) {
    add(element)
}
operator fun BigInteger.plus(other: Int): BigInteger = add(BigInteger("$other"))