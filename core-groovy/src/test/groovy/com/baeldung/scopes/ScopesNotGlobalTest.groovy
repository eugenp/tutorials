package com.baeldung.scopes

import spock.lang.Specification

class ScopesNotGlobalTest extends Specification {

    ScopesFail scopes

    void setup() {
        scopes = new ScopesFail()
    }

    def 'Should fail Scopes'() {
        given:
        scopes
        when:
        scopes.run()
        then:
        final MissingPropertyException exception = thrown()
        assert exception != null
    }
    def 'Should get local value via function'() {
        given:
        scopes
        when:
        def local = scopes.fLocal()
        then:
        assert local == 333
    }
}