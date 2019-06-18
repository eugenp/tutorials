package com.baeldung.scope

import org.junit.Test
import kotlin.test.assertTrue


class ScopeFunctionsUnitTest {

    class Logger {

        var called : Boolean = false

        fun info(message: String) {
            called = true
        }

        fun wasCalled() = called
    }

    @Test
    fun shouldTransformWhenLetFunctionUsed() {
        val stringBuider = StringBuilder()
        val numberOfCharacters = stringBuider.let {
            it.append("This is a transformation function.")
            it.append("It takes a StringBuilder instance and returns the number of characters in the generated String")
            it.length
        }

        assertTrue {
            numberOfCharacters == 128
        }
    }

    @Test
    fun shouldHandleNullabilityWhenLetFunctionUsed() {

        val message: String? = "hello there!"
        val charactersInMessage = message?.let {
            "At this point is safe to reference the variable. Let's print the message: $it"
        } ?: "default value"

        assertTrue {
            charactersInMessage.equals("At this point is safe to reference the variable. Let's print the message: hello there!")
        }

        val aNullMessage = null
        val thisIsNull = aNullMessage?.let {
            "At this point it would be safe to reference the variable. But it will not really happen because it is null. Let's reference: $it"
        } ?: "default value"

        assertTrue {
            thisIsNull.equals("default value")
        }
    }

    @Test
    fun shouldInitializeObjectWhenUsingApply() {
        val aStudent = Student().apply {
            studentId = "1234567"
            name = "Mary"
            surname = "Smith"
        }

        assertTrue {
            aStudent.name.equals("Mary")
        }
    }

    @Test
    fun shouldAllowBuilderStyleObjectDesignWhenApplyUsedInClassMethods() {
        val teacher = Teacher()
            .setId(1000)
            .setName("Martha")
            .setSurname("Spector")

        assertTrue {
            teacher.surname.equals("Spector")
        }
    }

    @Test
    fun shouldAllowSideEffectWhenUsingAlso() {
        val restClient = RestClient("http://www.someurl.com")

        val logger = Logger()

        val headers = restClient
            .getResponse()
            .also { logger.info(it.toString()) }
            .headers

        assertTrue {
            logger.wasCalled() && headers.headerInfo.equals("some header info")
        }

    }

    @Test
    fun shouldInitializeFieldWhenAlsoUsed() {
        val aStudent = Student().also { it.name = "John"}

        assertTrue {
            aStudent.name.equals("John")
        }
    }

    @Test
    fun shouldLogicallyGroupObjectCallsWhenUsingWith() {
        val bankAccount = BankAccount(1000)
        with (bankAccount) {
            checkAuthorization("someone")
            addPayee("some payee")
            makePayment("payment information")
        }
    }

    @Test
    fun shouldConvertObjectWhenRunUsed() {
        val stringBuider = StringBuilder()
        val numberOfCharacters = stringBuider.run {
            append("This is a transformation function.")
            append("It takes a StringBuilder instance and returns the number of characters in the generated String")
            length
        }

        assertTrue {
            numberOfCharacters == 128
        }
    }

    @Test
    fun shouldHandleNullabilityWhenRunIsUsed() {
        val message: String? = "hello there!"
        val charactersInMessage = message?.run {
            "At this point is safe to reference the variable. Let's print the message: $this"
        } ?: "default value"

        assertTrue {
            charactersInMessage.equals("At this point is safe to reference the variable. Let's print the message: hello there!")
        }
    }

}