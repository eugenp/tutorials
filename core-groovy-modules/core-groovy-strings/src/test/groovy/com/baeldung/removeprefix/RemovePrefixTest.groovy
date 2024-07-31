package com.baeldung.removeprefix

import spock.lang.Specification

class RemovePrefixTest extends Specification {

    def "whenCasePrefixIsRemoved_thenReturnTrue"() {
        given:
        def trimPrefix = {
            it.startsWith('Groovy-') ? it.minus('Groovy-') : it
        }

        when:
        def actual = trimPrefix("Groovy-Tutorials at Baeldung")
        def expected = "Tutorials at Baeldung"

        then:
        expected == actual
    }

    def "whenPrefixIsRemoved_thenReturnTrue"() {
        given:
        String prefix = "groovy-"
        String trimPrefix = "Groovy-Tutorials at Baeldung"

        when:
        def actual
        if (trimPrefix.startsWithIgnoreCase(prefix)) {
            actual = trimPrefix.substring(prefix.length())
        }
        def expected = "Tutorials at Baeldung"

        then:
        expected == actual
    }

    def "whenPrefixIsRemovedUsingRegex_thenReturnTrue"() {
        given:
        def regex = ~"^([Gg])roovy-"
        String trimPrefix = "Groovy-Tutorials at Baeldung"

        when:
        String actual = trimPrefix - regex
        def expected = "Tutorials at Baeldung"

        then:
        expected == actual
    }

    def "whenPrefixIsRemovedUsingReplaceFirst_thenReturnTrue"() {
        given:
        def regex = ~"^groovy"
        String trimPrefix = "groovyTutorials at Baeldung's groovy page"

        when:
        String actual = trimPrefix.replaceFirst(regex, "")
        def expected = "Tutorials at Baeldung's groovy page"

        then:
        expected == actual
    }

    def "whenPrefixIsRemovedUsingReplaceAll_thenReturnTrue"() {
        given:
        String trimPrefix = "groovyTutorials at Baeldung groovy"

        when:
        String actual = trimPrefix.replaceAll(/^groovy/, "")
        def expected = "Tutorials at Baeldung groovy"

        then:
        expected == actual
    }
}
