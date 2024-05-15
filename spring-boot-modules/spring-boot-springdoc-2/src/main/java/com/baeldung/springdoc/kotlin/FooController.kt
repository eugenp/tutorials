package com.baeldung.springdoc.kotlin

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.hibernate.internal.util.collections.CollectionHelper.listOf
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class FooController {
    val fooList: List<Foo> = listOf(Foo(1, "one"), Foo(2, "two"))

    @Operation(summary = "Get all foos")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Found Foos", content = [
                    (Content(
                        mediaType = "application/json", array = (
                                ArraySchema(schema = Schema(implementation = Foo::class)))
                    ))]
            ),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
            ApiResponse(responseCode = "404", description = "Did not find any Foos", content = [Content()])]
    )
    @GetMapping("/foo")
    fun getAllFoos(): List<Foo> = fooList
}


