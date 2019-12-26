package com.baeldung.jeekotlin.entity

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity
data class Student constructor (

    @SequenceGenerator(name = "student_id_seq", sequenceName = "student_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "student_id_seq", strategy = GenerationType.SEQUENCE)
    @Id
    var id: Long?,

    var firstName: String,

    var lastName: String

) {
    constructor() : this(null, "", "")

    constructor(firstName: String, lastName: String) : this(null, firstName, lastName)
}
