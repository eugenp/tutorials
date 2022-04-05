package com.baeldung.iteratemap

import com.baeldung.find.Person
import org.junit.Test

import static org.junit.Assert.*

class IterateMapUnitTest {

    @Test
    void whenUsingEach_thenMapIsIterated() {
        def map = [
                'FF0000' : 'Red',
                '00FF00' : 'Lime',
                '0000FF' : 'Blue',
                'FFFF00' : 'Yellow'
        ]

        map.each { println "Hex Code: $it.key = Color Name: $it.value" }
    }

    @Test
    void whenUsingEachWithEntry_thenMapIsIterated() {
        def map = [
                'E6E6FA' : 'Lavender',
                'D8BFD8' : 'Thistle',
                'DDA0DD' : 'Plum',
        ]

        map.each { entry -> println "Hex Code: $entry.key = Color Name: $entry.value" }
    }

    @Test
    void whenUsingEachWithKeyAndValue_thenMapIsIterated() {
        def map = [
                '000000' : 'Black',
                'FFFFFF' : 'White',
                '808080' : 'Gray'
        ]

        map.each { key, val ->
            println "Hex Code: $key = Color Name $val"
        }
    }

    @Test
    void whenUsingEachWithIndexAndEntry_thenMapIsIterated() {
        def map = [
                '800080' : 'Purple',
                '4B0082' : 'Indigo',
                '6A5ACD' : 'Slate Blue'
        ]

        map.eachWithIndex { entry, index ->
            def indent = ((index == 0 || index % 2 == 0) ? "   " : "")
            println "$indent Hex Code: $entry.key = Color Name: $entry.value"
        }
    }

    @Test
    void whenUsingEachWithIndexAndKeyAndValue_thenMapIsIterated() {
        def map = [
                'FFA07A' : 'Light Salmon',
                'FF7F50' : 'Coral',
                'FF6347' : 'Tomato',
                'FF4500' : 'Orange Red'
        ]

        map.eachWithIndex { key, val, index ->
            def indent = ((index == 0 || index % 2 == 0) ? "   " : "")
            println "$indent Hex Code: $key = Color Name: $val"
        }
    }

    @Test
    void whenUsingForLoop_thenMapIsIterated() {
        def map = [
                '2E8B57' : 'Seagreen',
                '228B22' : 'Forest Green',
                '008000' : 'Green'
        ]

        for (entry in map) {
            println "Hex Code: $entry.key = Color Name: $entry.value"
        }
    }
}
