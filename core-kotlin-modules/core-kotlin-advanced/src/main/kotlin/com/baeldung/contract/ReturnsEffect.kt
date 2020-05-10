package com.baeldung.contract

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

data class Request(val arg: String)

class Service {

    @ExperimentalContracts
    fun process(request: Request?) {
        validate(request)
        println(request.arg)
    }

}

@ExperimentalContracts
private fun validate(request: Request?) {
    contract {
        returns() implies (request != null)
    }
    if (request == null) {
        throw IllegalArgumentException("Undefined request")
    }
}

data class MyEvent(val message: String)

@ExperimentalContracts
fun processEvent(event: Any?) {
    if (isInterested(event)) {
        println(event.message) // Compiler makes smart cast here with the help of contract
    }
}

@ExperimentalContracts
fun isInterested(event: Any?): Boolean {
    contract {
        returns(true) implies (event is MyEvent)
    }
    return event is MyEvent
}