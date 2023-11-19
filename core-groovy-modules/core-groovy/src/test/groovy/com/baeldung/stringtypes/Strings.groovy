package groovy.com.baeldung.stringtypes

import spock.lang.Specification

class Strings extends Specification {

    def 'string interpolation '() {
        given:
        def name = "Kacper"

        when:
        def result = "Hello ${name}!"

        then:
        result.toString() == "Hello Kacper!"
    }

    def 'string concatenation'() {
        given:
        def first = "first"
        def second = "second"

        when:
        def concatenation = first + second

        then:
        concatenation == "firstsecond"
    }
}
