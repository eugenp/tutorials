package com.baeldung.channels

import java.text.SimpleDateFormat
import java.util.*

fun log(value: Any) {
    println(SimpleDateFormat("HH:MM:ss").format(Date()) + " - $value")
}