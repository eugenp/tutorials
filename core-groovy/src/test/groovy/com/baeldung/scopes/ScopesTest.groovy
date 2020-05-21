package com.baeldung.scopes


import org.spockframework.runtime.InvalidSpecException
import spock.lang.Specification

class ScopesTest extends Specification {

    Scopes scopes

    void setup() {
        scopes = new Scopes()
    }

    def 'Should run Scopes'() {
        given:
            scopes
        when:
            scopes.run()
        then:
            final MissingPropertyException exception = thrown()
            assert exception != null
    }
}