package com.baeldung.dates.datetime

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class UseZonedDateTime {

    fun getZonedDateTime(localDateTime: LocalDateTime, zoneId: ZoneId): ZonedDateTime {
        return ZonedDateTime.of(localDateTime, zoneId)
    }
}