package com.baeldung.kotlin.jackson

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class Book(var title: String, @JsonProperty("author") var authorName: String) {
    var genres: List<String>? = emptyList()
}