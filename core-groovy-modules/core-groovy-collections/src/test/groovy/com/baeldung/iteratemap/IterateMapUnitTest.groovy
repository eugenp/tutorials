package com.baeldung.iteratemap

import spock.lang.Specification

class IterateMapUnitTest extends Specification {

    final Map map = [
            'FF0000': 'Red',
            '00FF00': 'Lime',
            '0000FF': 'Blue',
            'FFFF00': 'Yellow',
            'E6E6FA': 'Lavender',
            'D8BFD8': 'Thistle',
            'DDA0DD': 'Plum',
    ]

    def "whenUsingEach_thenMapIsIterated"() {
        expect:
        map.each { println "Hex Code: $it.key = Color Name: $it.value" }
    }

    def "whenUsingEachWithEntry_thenMapIsIterated"() {
        expect:
        map.each { entry -> println "Hex Code: $entry.key = Color Name: $entry.value" }
    }

    def "whenUsingEachWithKeyAndValue_thenMapIsIterated"() {
        expect:
        map.each { key, val ->
            println "Hex Code: $key = Color Name $val"
        }
    }

    def "whenUsingEachWithIndexAndEntry_thenMapIsIterated"() {
        expect:
        map.eachWithIndex { entry, index ->
            def indent = index % 2 == 0 ? "   " : ""
            println "$indent Hex Code: $entry.key = Color Name: $entry.value"
        }
    }

    def "whenUsingEachWithIndexAndKeyAndValue_thenMapIsIterated"() {
        expect:
        map.eachWithIndex { key, val, index ->
            def indent = index % 2 == 0 ? "   " : ""
            println "$indent Hex Code: $key = Color Name: $val"
        }
    }

    def "whenUsingForLoop_thenMapIsIterated"() {
        expect:
        for (entry in map) {
            println "Hex Code: $entry.key = Color Name: $entry.value"
        }
    }
}
