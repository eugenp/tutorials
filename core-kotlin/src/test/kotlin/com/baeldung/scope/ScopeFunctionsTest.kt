package com.baeldung.scope

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ScopeFunctionsTest {
    
    class Logger {

        var called : Boolean = false

        fun info(message: String) {
            called = true
        }

        fun wasCalled() = called
    }

    @Test
    fun testLetFunctionForTransformation() {
        val stringBuider = StringBuilder()
        val numberOfCharacters = stringBuider.let {
            it.append("This is a transformation function.")
            it.append("It takes a StringBuilder instance and returns the number of characters in the generated String")
            it.length
        }
        assertThat(numberOfCharacters).isEqualTo(128)
    }

    @Test
    fun testLetFunctionToHandleNullability() {

        val message: String? = "hello there!"
        val charactersInMessage = message?.let {
            "At this point is safe to reference the variable. Let's print the message: $it"
        } ?: "default value"

        assertThat(charactersInMessage).isEqualTo("At this point is safe to reference the variable. Let's print the message: hello there!")

        val aNullMessage = null
        val thisIsNull = aNullMessage?.let {
            "At this point it would be safe to reference the variable. But it will not really happen because it is null. Let's reference: $it"
        } ?: "default value"

        assertThat(thisIsNull).isEqualTo("default value")
    }

    @Test
    fun testApplyForObjectInitialization() {
        val aStudent = Student().apply {
            studentId = "1234567"
            name = "Mary"
            surname = "Smith"
        }
        assertThat(aStudent.name).isEqualTo("Mary")
    }

    @Test
    fun testApplyForBuilderStyleObjectDesign() {
        val teacher = Teacher()
            .setId(1000)
            .setName("Martha")
            .setSurname("Spector")

        assertThat(teacher.surname).isEqualTo("Spector")
    }

    @Test
    fun testAlsoSideEffect() {
        val restClient = RestClient("http://www.someurl.com")

        val logger = Logger()

        val headers = restClient
            .getResponse()
            .also { logger.info(it.toString()) }
            .headers

        assertThat(logger.wasCalled()).isTrue()
        assertThat(headers.headerInfo).isEqualTo("some header info")
    }

    @Test
    fun testAlsoInitialization() {
        val aStudent = Student().also { it.name = "John"}
        assertThat(aStudent.name).isEqualTo("John")
    }

    @Test
    fun testWithObjectLogicallyGrouping() {
        val bankAccount = BankAccount(1000)
        with (bankAccount) {
            checkAuthorization("someone")
            addPayee("some payee")
            makePayment("payment information")
        }
    }

    @Test
    fun testRunObjectConversion() {
        val stringBuider = StringBuilder()
        val numberOfCharacters = stringBuider.run {
            append("This is a transformation function.")
            append("It takes a StringBuilder instance and returns the number of characters in the generated String")
            length
        }
        assertThat(numberOfCharacters).isEqualTo(128)
    }

    @Test
    fun testRunNullabilityHandling() {
        val message: String? = "hello there!"
        val charactersInMessage = message?.run {
            "At this point is safe to reference the variable. Let's print the message: $this"
        } ?: "default value"
        assertThat(charactersInMessage).isEqualTo("At this point is safe to reference the variable. Let's print the message: hello there!")
    }

}