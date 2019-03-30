package com.baeldung.springreactivekotlin

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

import org.springframework.web.reactive.function.BodyInserters.fromObject

@Configuration
class SimpleRoute {
    @Bean
    fun route() = router {
        GET("/route") { _ -> ServerResponse.ok().body(fromObject(arrayOf(1, 2, 3))) }
    }
}