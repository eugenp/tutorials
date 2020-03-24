package com.baeldung.static

class ConsoleUtils {
    companion object {
        @JvmStatic
        fun debug(debugMessage : String) {
            println("[DEBUG] $debugMessage")
        }
    }
}