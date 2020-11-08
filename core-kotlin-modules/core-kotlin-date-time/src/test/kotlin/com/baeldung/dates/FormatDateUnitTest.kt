package com.baeldung.kotlin.dates

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FormatDateUnitTest {

    @Test
    fun givenDate_whenDefaultFormat_thenFormattedString() {

        var date = LocalDate.parse("2018-12-31")

        assertThat(date.toString()).isEqualTo("2018-12-31")
    }

    @Test
    fun givenDate_whenCustomFormat_thenFormattedString() {

        var date = LocalDate.parse("2018-12-31")

        var formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
        var formattedDate = date.format(formatter)

        assertThat(formattedDate).isEqualTo("31-December-2018")
    }

}