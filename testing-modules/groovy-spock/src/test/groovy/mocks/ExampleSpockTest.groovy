package mocks

import spock.lang.Specification

class ExampleSpockTest extends Specification {

    CharacterCalculator characterCalculator

    def setup() {
        characterCalculator = new CharacterCalculator()
    }

    def 'should calculate character occurrences in given string'() {
        given:
        char characterToCount = 'o'
        String value = 'Hello world!'

        when:
        int result = characterCalculator.countCharacterInString(value, characterToCount)

        then:
        result == 2
    }


}
