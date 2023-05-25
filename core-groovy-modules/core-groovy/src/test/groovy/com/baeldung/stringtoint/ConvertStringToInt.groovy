package com.baeldung.stringtoint

import spock.lang.Specification

import java.text.DecimalFormat

class ConvertStringToInt extends Specification {

    final String STRING_NUM = "123"
    final int EXPECTED_INT = 123

    def "givenString_whenUsingAsInteger_thenConvertToInteger"() {
        given:
        def invalidString = "123a"
        Integer integerNum = STRING_NUM as Integer

        when:
        def intNum = invalidString?.isInteger() ? invalidString as Integer : null

        then:
        intNum == null
        integerNum == EXPECTED_INT
    }

    def "givenString_whenUsingAsInt_thenConvertToInt"() {
        given:
        int intNum = STRING_NUM as int

        expect:
        intNum == EXPECTED_INT
    }

    def "givenString_whenUsingToInteger_thenConvertToInteger"() {
        given:
        int intNum = STRING_NUM.toInteger()

        expect:
        intNum == EXPECTED_INT
    }

    def "givenString_whenUsingParseInt_thenConvertToInteger"() {
        given:
        int intNum = Integer.parseInt(STRING_NUM)

        expect:
        intNum == EXPECTED_INT
    }

    def "givenString_whenUsingValueOf_thenConvertToInteger"() {
        given:
        int intNum = Integer.valueOf(STRING_NUM)

        expect:
        intNum == EXPECTED_INT
    }

    def "givenString_whenUsingIntValue_thenConvertToInteger"() {
        given:
        int intNum = Integer.valueOf(STRING_NUM).intValue()

        expect:
        intNum == EXPECTED_INT
    }

    def "givenString_whenUsingNewInteger_thenConvertToInteger"() {
        given:
        Integer intNum = Integer.valueOf(STRING_NUM)

        expect:
        intNum == EXPECTED_INT
    }

    def "givenString_whenUsingDecimalFormat_thenConvertToInteger"() {
        given:
        DecimalFormat decimalFormat = new DecimalFormat("#")

        when:
        int intNum = decimalFormat.parse(STRING_NUM).intValue()

        then:
        intNum == EXPECTED_INT
    }

    def "givenInvalidString_whenUsingAs_thenThrowNumberFormatException"() {
        given:
        def invalidString = "123a"

        when:
        invalidString as Integer

        then:
        thrown(NumberFormatException)
    }

    def "givenNullString_whenUsingToInteger_thenThrowNullPointerException"() {
        given:
        def invalidString = null

        when:
        invalidString.toInteger()

        then:
        thrown(NullPointerException)
    }

    def "givenString_whenUsingIsInteger_thenCheckIfCorrectValue"() {
        given:
        def invalidString = "123a"
        def validString = "123"

        when:
        def invalidNum = invalidString?.isInteger() ? invalidString as Integer : false
        def correctNum = validString?.isInteger() ? validString as Integer : false

        then:
        !invalidNum
        correctNum == 123
    }
}
