package com.baeldung.dates.datetime

import java.time.LocalDateTime

class UseLocalDateTime {

    fun getLocalDateTimeUsingParseMethod(representation: String): LocalDateTime {
        return LocalDateTime.parse(representation)
    }
}