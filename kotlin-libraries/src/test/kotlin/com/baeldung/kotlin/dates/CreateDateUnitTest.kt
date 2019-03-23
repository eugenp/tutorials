package com.baeldung.kotlin.dates

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CreateDateUnitTest {

    @Test
    fun givenString_whenDefaultFormat_thenCreated() {

        var date = LocalDate.parse("2018-12-31")

        assertThat(date).isEqualTo("2018-12-31")
    }

    @Test
    fun givenString_whenCustomFormat_thenCreated() {

        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        var date = LocalDate.parse("31-12-2018", formatter)

        assertThat(date).isEqualTo("2018-12-31")
    }

    @Test
    fun givenYMD_whenUsingOf_thenCreated() {
        var date = LocalDate.of(2018, 12, 31)

        assertThat(date).isEqualTo("2018-12-31")
    }

}