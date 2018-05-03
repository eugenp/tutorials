package com.baeldung.klaxon

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.JsonReader
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.beust.klaxon.double
import com.beust.klaxon.int
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import java.io.StringReader
import kotlin.test.assertEquals

class KlaxonUnitTest {

    @Test
    fun giveJsonString_whenSerialize_thenGetPerson() {
        val result = Klaxon().parse<Person>("""
            {
                "name": "John"
            }
        """)

        assertEquals("John", result?.name)
    }

    @Test
    fun givePerson_whenDeserialize_thenGetJsonString() {
        val person = Person("John")
        val result = Klaxon().toJsonString(person)

        assertEquals("""{"name" : "John"}""", result)
    }

    @Test
    fun giveJsonArray_whenStreaming_thenGetPersonArray() {
        val jsonArray = """[
            { "name": "John", "age": 31 },
            { "name": "Mary", "age": 30 }
        ]"""
        val expectedArray = arrayListOf(PersonData("John", 31),
                PersonData("Mary", 30))
        val klaxon = Klaxon()
        val personArray = arrayListOf<PersonData>()
        JsonReader(StringReader(jsonArray)).use { reader ->
            reader.beginArray {
                while (reader.hasNext()) {
                    val person = klaxon.parse<PersonData>(reader)
                    personArray.add(person!!)
                }
            }
        }

        assertEquals(expectedArray, personArray)
    }

    @Test
    fun giveJsonString_whenParser_thenGetJsonObject() {
        val jsonString = StringBuilder("""{ "name": "David", "age": 35, "language": "EN"}""")
        val parser = Parser()
        val json = parser.parse(jsonString) as JsonObject

        assertAll(
                Executable { assertEquals("David", json["name"]) },
                Executable { assertEquals(35, json["age"]) },
                Executable { assertEquals("EN", json["language"]) }
        )
    }

    @Test
    fun giveJsonStringArray_whenParser_thenGetJsonArray() {
        val jsonString = StringBuilder("""[{ "name": "Magazine" }, { "product": "Kotlin" }, { "price": 15 }]""")
        val parser = Parser()
        val json = parser.parse(jsonString) as JsonArray<JsonObject>

        assertAll(
                Executable { assertEquals("Magazine", json[0]["name"]) },
                Executable { assertEquals("Kotlin", json[1]["product"]) },
                Executable { assertEquals(15, json[2].int("price")) }
        )
    }

}