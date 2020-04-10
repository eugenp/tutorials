package com.baeldung.bootmicroservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HealthTrackerApplication

fun main(args: Array<String>) {
	runApplication<HealthTrackerApplication>(*args)
}
