package com.baeldung.bootmicroservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table
data class Profile(@Id var id:Long?, var firstName : String, var lastName : String, var birthDate: LocalDateTime)