package com.baeldung.contract

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@ExperimentalContracts
inline fun <R> myRun(block: () -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return block()
}

@ExperimentalContracts
fun callsInPlace() {
    val i: Int
    myRun {
        i = 1 // Without contract initialization is forbidden due to possible re-assignment
    }
    println(i) // Without contract variable might be uninitialized
}

