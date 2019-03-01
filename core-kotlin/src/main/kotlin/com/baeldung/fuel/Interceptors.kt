package com.baeldung.fuel

import com.github.kittinunf.fuel.core.Request

fun tokenInterceptor() = {
    next: (Request) -> Request ->
    { req: Request ->
        req.header(mapOf("Authorization" to "Bearer AbCdEf123456"))
        next(req)
    }
}