package com.baeldung.anonymous

import java.io.Serializable
import java.nio.channels.Channel

fun main() {
    val channel = object : Channel {
        override fun isOpen() = false

        override fun close() {
        }
    }

    val maxEntries = 10
    val lruCache = object : LinkedHashMap<String, Int>(10, 0.75f) {

        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, Int>?): Boolean {
            return size > maxEntries
        }
    }

    val map = object : LinkedHashMap<String, Int>() {
        // omitted
    }

    val serializableChannel = object : Channel, Serializable {
        override fun isOpen(): Boolean {
            TODO("Not yet implemented")
        }

        override fun close() {
            TODO("Not yet implemented")
        }
    }

    val obj = object {
        val question = "answer"
        val answer = 42
    }
    println("The ${obj.question} is ${obj.answer}")
}