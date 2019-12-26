package com.baeldung

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration

@SpringBootApplication(exclude = arrayOf(MongoReactiveDataAutoConfiguration::class))
class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
