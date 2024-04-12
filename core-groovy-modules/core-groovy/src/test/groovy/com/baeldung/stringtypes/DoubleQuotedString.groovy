package groovy.com.baeldung.stringtypes

import spock.lang.Specification

class DoubleQuotedString extends Specification {

    def 'escape double quoted string'() {
        given:
        def example = "Hello \"world\"!"

        expect:
        example == 'Hello "world"!'
    }

    def 'String ang GString'() {
        given:
        def string = "example"
        def stringWithExpression = "example${2}"

        expect:
        string instanceof String
        stringWithExpression instanceof GString
        stringWithExpression.toString() instanceof String
    }

    def 'placeholder with variable'() {
        given:
        def name = "John"

        when:
        def helloName = "Hello $name!".toString()

        then:
        helloName == "Hello John!"
    }

    def 'placeholder with expression'() {
        given:
        def result = "result is ${2 * 2}".toString()

        expect:
        result == "result is 4"
    }

    def 'placeholder with dotted access'() {
        given:
        def person = [name: 'John']

        when:
        def myNameIs = "I'm $person.name, and you?".toString()

        then:
        myNameIs == "I'm John, and you?"
    }

    def 'placeholder with method call'() {
        given:
        def name = 'John'

        when:
        def result = "Uppercase name: ${name.toUpperCase()}".toString()

        then:
        result == "Uppercase name: JOHN"
    }


    def 'GString and String hashcode'() {
        given:
        def string = "2+2 is 4"
        def gstring = "2+2 is ${4}"

        expect:
        string.hashCode() != gstring.hashCode()
    }
}
