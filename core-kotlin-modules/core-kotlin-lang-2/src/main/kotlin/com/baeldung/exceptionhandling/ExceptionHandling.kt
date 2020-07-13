package com.baeldung.exceptionhandling

import java.io.IOException

class ExceptionHandling {

    fun tryCatchBlock(): Int? {
        try {
            val message = "Welcome to Kotlin Tutorials"
            return message.toInt()
        } catch (exception: NumberFormatException) {
            println("NumberFormatException in the code")
            return null
        }
    }

    fun tryCatchExpression(): Int? {
        val number = try {
            val message = "Welcome to Kotlin Tutorials"
            message.toInt()
        } catch (exception: NumberFormatException) {
            println("NumberFormatException in the code")
            null
        }
        return number
    }

    fun multipleCatchBlock(): Int? {
        return try {
            val result = 25 / 0
            return result
        } catch (exception: NumberFormatException) {
            println("NumberFormatException in the code")
            null
        } catch (exception: ArithmeticException) {
            println("ArithmeticException in the code")
            null
        } catch (exception: Exception) {
            println("Exception in the code")
            null
        }
    }

    fun nestedTryCatchBlock(): Int? {
        return try {
            val firstNumber = 50 / 2 * 0
            try {
                val secondNumber = 100 / firstNumber
                secondNumber
            } catch (exception: ArithmeticException) {
                println("ArithmeticException in the code")
                null
            }
        } catch (exception: NumberFormatException) {
            println("NumberFormatException in the code")
            null
        }
    }

    fun finallyBlock(): Int? {
        return try {
            val message = "Welcome to Kotlin Tutorials"
            message.toInt()
        } catch (exception: NumberFormatException) {
            println("NumberFormatException in the code")
            null
        } finally {
            println("In the Finally block")
        }
    }

    fun throwKeyword() {
        val message = "Welcome to Kotlin Tutorials"
        if (message.length > 10) throw IllegalArgumentException("String is invalid")
        else println("String is valid")
    }

    @Throws(IOException::class)
    fun throwsAnnotation(): String?{
        val filePath = null
        return filePath ?: throw IOException("File path is invalid")
    }
}
