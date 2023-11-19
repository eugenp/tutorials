package com.baeldung.springdoc.kotlin

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
data class Foo(
    @Id
    val id: Long = 0,

    @NotBlank
    @Size(min = 0, max = 50)
    val name: String = ""
)