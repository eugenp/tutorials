package com.baeldung.springreactivekotlin

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.web.reactive.function.server.router

@Configuration
class HomeSensorsRouters(private val handler: HomeSensorsHandler) {

    @Bean
    fun roomsRouter() = router {
        (accept(TEXT_HTML) and "/room").nest {
            GET("/light", handler::getLightReading)
            POST("/light", handler::setLight)
        }
    }

    @Bean
    fun deviceRouter() = router {
        accept(TEXT_HTML).nest {
            (GET("/device/") or GET("/devices/")).invoke(handler::getAllDevices)
            GET("/device/{id}", handler::getDeviceReadings)
        }
        (accept(APPLICATION_JSON) and "/api").nest {
            (GET("/device/") or GET("/devices/")).invoke(handler::getAllDeviceApi)
            POST("/device/", handler::setDeviceReadingApi)
        }
    }

}
