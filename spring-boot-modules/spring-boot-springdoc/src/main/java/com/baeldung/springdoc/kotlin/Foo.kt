package com.baeldung.springdoc.kotlin

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
data class Foo(
    @Id
    val id: Long = 0,
		
    @NotBlank
    @Size(min = 0, max = 50)
    val name: String = ""
)