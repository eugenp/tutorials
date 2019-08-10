package com.baeldung.kotlin.mockmvc

import com.fasterxml.jackson.annotation.JsonInclude

data class Name(val first: String, val last: String)

data class Request(val name: Name)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Response(val error: String?)