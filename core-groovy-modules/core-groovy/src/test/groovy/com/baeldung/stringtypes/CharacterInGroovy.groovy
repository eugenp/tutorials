package groovy.com.baeldung.stringtypes

import spock.lang.Specification

class CharacterInGroovy extends Specification {

    def 'character'() {
        given:
        char a = 'A' as char
        char b = 'B' as char
        char c = (char) 'C'

        expect:
        a instanceof Character
        b instanceof Character
        c instanceof Character
    }
}
