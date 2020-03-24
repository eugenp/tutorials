package com.baeldung.klaxon

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.JsonReader
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.beust.klaxon.PathMatcher
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.Assert
import org.junit.jupiter.api.Test
import java.io.StringReader
import java.util.regex.Pattern

class KlaxonUnitTest {

    @Test
    fun giveProduct_whenSerialize_thenGetJsonString() {
        val product = Product("HDD")
        val result = Klaxon().toJsonString(product)

        assertThat(result).isEqualTo("""{"name" : "HDD"}""")
    }

    @Test
    fun giveJsonString_whenDeserialize_thenGetProduct() {
        val result = Klaxon().parse<Product>("""
            {
                "name" : "RAM"
            }
        """)

        assertThat(result?.name).isEqualTo("RAM")
    }

    @Test
    fun giveCustomProduct_whenSerialize_thenGetJsonString() {
        val product = CustomProduct("HDD", 1)
        val result = Klaxon().toJsonString(product)

        assertThat(result).isEqualTo("""{"productName" : "HDD"}""")
    }

    @Test
    fun giveJsonArray_whenStreaming_thenGetProductArray() {
        val jsonArray = """
        [
            { "name" : "HDD", "capacityInGb" : 512 },
            { "name" : "RAM", "capacityInGb" : 16 }
        ]"""
        val expectedArray = arrayListOf(ProductData("HDD", 512),
                ProductData("RAM", 16))
        val klaxon = Klaxon()
        val productArray = arrayListOf<ProductData>()
        JsonReader(StringReader(jsonArray)).use { reader ->
            reader.beginArray {
                while (reader.hasNext()) {
                    val product = klaxon.parse<ProductData>(reader)
                    productArray.add(product!!)
                }
            }
        }

        assertThat(productArray).hasSize(2).isEqualTo(expectedArray)
    }

    @Test
    fun giveJsonString_whenParser_thenGetJsonObject() {
        val jsonString = StringBuilder("""
            {
                "name" : "HDD",
                "capacityInGb" : 512,
                "sizeInInch" : 2.5
            }
        """)
        val parser = Parser()
        val json = parser.parse(jsonString) as JsonObject

        assertThat(json).hasSize(3).containsEntry("name", "HDD").containsEntry("capacityInGb", 512).containsEntry("sizeInInch", 2.5)
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun giveJsonStringArray_whenParser_thenGetJsonArray() {
        val jsonString = StringBuilder("""
        [
            { "name" : "SDD" },
            { "madeIn" : "Taiwan" },
            { "warrantyInYears" : 5 }
        ]""")
        val parser = Parser()
        val json = parser.parse(jsonString) as JsonArray<JsonObject>

        assertSoftly({ softly ->
            softly.assertThat(json).hasSize(3)
            softly.assertThat(json[0]["name"]).isEqualTo("SDD")
            softly.assertThat(json[1]["madeIn"]).isEqualTo("Taiwan")
            softly.assertThat(json[2]["warrantyInYears"]).isEqualTo(5)
        })
    }

    @Test
    fun givenJsonString_whenStreaming_thenProcess() {
        val jsonString = """
        {
             "name" : "HDD",
             "madeIn" : "Taiwan",
             "warrantyInYears" : 5
             "hasStock" : true
             "capacitiesInTb" : [ 1, 2 ],
             "features" : { "cacheInMb" : 64, "speedInRpm" : 7200 }
        }"""

        JsonReader(StringReader(jsonString)).use { reader ->
            reader.beginObject {
                while (reader.hasNext()) {
                    val readName = reader.nextName()
                    when (readName) {
                        "name" -> assertThat(reader.nextString()).isEqualTo("HDD")
                        "madeIn" -> assertThat(reader.nextString()).isEqualTo("Taiwan")
                        "warrantyInYears" -> assertThat(reader.nextInt()).isEqualTo(5)
                        "hasStock" -> assertThat(reader.nextBoolean()).isEqualTo(true)
                        "capacitiesInTb" -> assertThat(reader.nextArray()).contains(1, 2)
                        "features" -> assertThat(reader.nextObject()).containsEntry("cacheInMb", 64).containsEntry("speedInRpm", 7200)
                        else -> Assert.fail("Unexpected name: $readName")
                    }
                }
            }
        }

    }

    @Test
    fun givenDiskInventory_whenRegexMatches_thenGetTypes() {
        val jsonString = """
    {
       "inventory" : {
           "disks" : [
               {
                   "type" : "HDD",
                   "sizeInGb" : 1000
               },
               {
                   "type" : "SDD",
                   "sizeInGb" : 512
               }
           ]
       }
    }"""
        val pathMatcher = object : PathMatcher {
            override fun pathMatches(path: String) = Pattern.matches(".*inventory.*disks.*type.*", path)

            override fun onMatch(path: String, value: Any) {
                when (path) {
                    "$.inventory.disks[0].type" -> assertThat(value).isEqualTo("HDD")
                    "$.inventory.disks[1].type" -> assertThat(value).isEqualTo("SDD")
                }
            }
        }
        Klaxon().pathMatcher(pathMatcher).parseJsonObject(StringReader(jsonString))
    }
}