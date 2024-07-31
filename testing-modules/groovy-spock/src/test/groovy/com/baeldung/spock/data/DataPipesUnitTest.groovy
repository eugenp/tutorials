package com.baeldung.spock.data

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title("Test various ways of using data pipes")
class DataPipesUnitTest extends Specification {

    @Subject
    def dataPipesSubject = new DataPipesSubject()

    def "given two numbers when we add them then our result is the sum of the inputs"() {
        given: "some inputs"
        def first = 1
        def second = 2

        and: "an expected expectedResult"
        def expectedResult = 3

        when: "we add them together"
        def result = dataPipesSubject.addWithATwist(first, second)

        then: "we get our expected answer"
        result == expectedResult
    }


    def "given a where clause with our inputs when we add them then our result is the sum of the inputs"() {
        when: "we add our inputs together"
        def result = dataPipesSubject.addWithATwist(first, second)

        then: "we get our expected answer"
        result == expectedResult

        where: "we have various inputs"
        first = 1
        second = 2
        expectedResult = 3
    }


    def "given an expect block to simplify our test when we add our inputs then our result is the sum of the two numbers"() {
        expect: "our addition to get the right result"
        dataPipesSubject.addWithATwist(first, second) == expectedResult

        where: "we have various inputs"
        first = 1
        second = 2
        expectedResult = 3
    }


    def "given some declared method parameters when we add our inputs then those types are used"(int first, int second, int expectedResult) {
        expect: "our addition to get the right result"
        dataPipesSubject.addWithATwist(first, second) == expectedResult

        where: "we have various inputs"
        first = 1
        second = 2
        expectedResult = 3
    }


    def "given data pipes when we add our inputs then our inputs are supplied from the data pipes"() {
        expect: "our addition to get the right result"
        dataPipesSubject.addWithATwist(first, second) == expectedResult

        where: "we have various inputs"
        first << [1]
        second << [2]
        expectedResult << [3]
    }


    def "given two numbers from a combined data pipe, #first and #second, when we add our inputs then the result is matches the value from our expectedResult data pipe: #expectedResult"() {
        expect: "our addition to get the right result"
        dataPipesSubject.addWithATwist(first, second) == expectedResult

        where: "we have various inputs"
        [first, second] << [
                [1, 2],
                [2, 2],
                [3, 5]
        ]

        and: "an expected expectedResult"
        expectedResult << [3, 4, 8]
    }


    def "given two numbers from a combined data pipe, #first and #second, when we add our inputs then our test runs for each set of numbers"() {
        expect: "our addition to get the right result"
        dataPipesSubject.addWithATwist(first, second) == expectedResult

        where: "we have various inputs"
        [first, second] << [
                [1, 2],
                [2, 2],
                [3, 5]
        ]

        and: "an expected expectedResult"
        expectedResult = first + second
    }


    def "given multiple scenarios in our data pipes when we add our inputs then our test runs for each set of numbers"() {
        expect: "our addition to get the right result"
        dataPipesSubject.addWithATwist(first, second) == expectedResult

        where: "we have various inputs"
        first << [1, 2, 3]
        second << [2, 2, 5]
        expectedResult << [3, 4, 8]
    }


    def "given a combined data feed when we add our numbers then our test runs for each set of numbers, #first and #second"() {
        expect: "our addition to get the right result"
        dataPipesSubject.addWithATwist(first, second) == expectedResult

        where: "we have various inputs"
        [first, second, expectedResult] << [
                [1, 2, 3],
                [2, 2, 4],
                [3, 5, 8]
        ]
    }


    def "given a map with our data when we add our inputs from a map then our test runs for each set of numbers, #first and #second"() {
        expect: "our addition to get the right result"
        dataPipesSubject.addWithATwist(first, second) == expectedResult

        where: "we have various inputs in the form of a map"
        [first, second, expectedResult] << [
                [
                        first         : 1,
                        second        : 2,
                        expectedResult: 3
                ],
                [
                        first         : 2,
                        second        : 2,
                        expectedResult: 4
                ]
        ]
    }


    def "given a method to supply our data when we add our inputs then our test runs for each set of numbers, #first and #second"() {
        expect: "our addition to get the right result"
        dataPipesSubject.addWithATwist(first, second) == expectedResult

        where: "we have various inputs in the form of a map"
        [first, second, expectedResult] << dataFeed()
    }

    def dataFeed() {
        [
                [
                        first         : 1,
                        second        : 2,
                        expectedResult: 3
                ],
                [
                        first         : 2,
                        second        : 2,
                        expectedResult: 4
                ]
        ]
    }


    def "given a table to supply our data when we add our inputs then our test runs for each set of numbers, #first and #second"() {
        expect: "our addition to get the right result"
        dataPipesSubject.addWithATwist(first, second) == expectedResult

        where: "we have various inputs"
        first | second || expectedResult
        1     | 2      || 3
        2     | 2      || 4
        3     | 5      || 8
    }


    def "given a semi-colon as table separator when we add our inputs then our test runs for each set of numbers, #first and #second"() {
        expect: "our addition to get the right result"
        dataPipesSubject.addWithATwist(first, second) == expectedResult

        where: "we have various inputs"
        first; second; expectedResult
        1; 2; 3
        2; 2; 4
        3; 5; 8
        // Since IDE Code formatting doesn't understand Spock's use of ';' separator
        // and inserts an extra space between ';;' it breaks the table syntax,
        // so, to avoid this formatting issue we've just used a single ';' separator between inputs and outputs
        // instead of our preferred double ';;'
    }


    def "given a larger table split into two tables when we add our inputs then our test runs for each set of numbers, #first and #second\"() {"() {
        expect: "our addition to get the right result"
        dataPipesSubject.addWithATwist(first, second) == expectedResult

        where: "we have various inputs"
        first | second
        1     | 2
        2     | 2
        3     | 5
        __
        expectedResult | _
        3              | _
        4              | _
        8              | _
    }


    def "given some additional scenarios when we add our inputs then our code coverage increases"() {
        expect: "our addition to get the right result"
        dataPipesSubject.addWithATwist(first, second) == expectedResult

        where: "we have various inputs"
        first | second || expectedResult
        1     | 2      || 3
        2     | 2      || 4
        3     | 5      || 8
        42    | 10     || 42
        1     | 42     || 42
    }

    static def STATIC_VARIABLE = 'When we have a very long string we can use a static variable'
    @Shared
    def SHARED_VARIABLE = 'When we have a very long string we can annotate our variable with @Shared'

    def "given long strings when our tables our too big then we can use shared or static variables to shorten the table"() {
        expect: "our addition to get the right result"
        dataPipesSubject.addExclamation(longString) == expectedResult

        where: "we have various inputs"
        longString                                                                                                  || expectedResult
        'When we have a very long string we can use a static or @Shared variable to make our tables easier to read' || 'When we have a very long string we can use a static or @Shared variable to make our tables easier to read!'
        STATIC_VARIABLE                                                                                             || "$STATIC_VARIABLE!"
        SHARED_VARIABLE                                                                                             || "$SHARED_VARIABLE!"
    }


    def "given a #scenario case when we add our inputs, #first and #second, then we get our expected result: #expectedResult"() {
        expect: "our addition to get the right result"
        dataPipesSubject.addWithATwist(first, second) == expectedResult

        where: "we have various inputs"
        scenario              | first | second || expectedResult
        "simple"              | 1     | 2      || 3
        "double 2"            | 2     | 2      || 4
        "double 2 referenced" | 2     | first  || first + second
        "three plus eight"    | 3     | 5      || 8
        "first special"       | 42    | 10     || 42
        "second special"      | 1     | 42     || 42
    }

}
