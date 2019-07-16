package com.baeldung.jeekotlin.rest

import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application

@ApplicationPath("/")
class ApplicationConfig : Application() {
    override fun getClasses() = setOf(StudentResource::class.java)
}
