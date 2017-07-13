package com.baeldung.springbootkotlin

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController(val helloService: HelloService) {

    @GetMapping("/hello")
    fun helloKotlin(): String {
        return "hello world"
    }

    @GetMapping("/hello-service")
    fun helloKotlinService(): String {
        return helloService.getHello()
    }

    @GetMapping("/hello-dto")
    fun helloDto(): HelloDto {
        return HelloDto("Hello from the dto")
    }
}