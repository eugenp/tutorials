package com.baeldung.strings

import spock.lang.Specification

import java.util.regex.Pattern

class StringMatchingSpec extends Specification {

    def "pattern operator example"() {
        given: "a pattern"
        def p = ~'foo'

        expect:
        p instanceof Pattern

        and: "you can use slash strings to avoid escaping of blackslash"
        def digitPattern = ~/\d*/
        digitPattern.matcher('4711').matches()
    }

    def "match operator example"() {
        expect:
        'foobar' ==~ /.*oba.*/

        and: "matching is strict"
        !('foobar' ==~ /foo/)
    }

    def "find operator example"() {
        when: "using the find operator"
        def matcher = 'foo and bar, baz and buz' =~ /(\w+) and (\w+)/

        then: "will find groups"
        matcher.size() == 2

        and: "can access groups using array"
        matcher[0][0] == 'foo and bar'
        matcher[1][2] == 'buz'

        and: "you can use it as a predicate"
        'foobarbaz' =~ /bar/
    }

}
