package com.baeldung.mockk

class TestableService {
    fun getDataFromDb(testParameter: String): String {
        // query database and return matching value
        return "Value from DB"
    }

    fun doSomethingElse(testParameter: String): String  {
        return "I don't want to!"
    }
}