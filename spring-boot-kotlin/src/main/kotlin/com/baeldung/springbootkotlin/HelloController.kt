package com.baeldung.springbootkotlin

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController @Autowired constructor(val helloService: HelloService) {

    @GetMapping("/hello")
    fun helloKotlin(): String {
        return "hello world"
    }

    @GetMapping("/hello-service")
    fun helloKotlinService(): String {
        return helloService.getHello()
    }

    @GetMapping("/hello-poko")
    fun helloPoko(): HelloPoko {
        return HelloPoko("Hello from the poko")
    }
}