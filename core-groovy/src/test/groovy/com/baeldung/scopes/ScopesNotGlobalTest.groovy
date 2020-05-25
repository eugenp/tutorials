package com.baeldung.scopes

import spock.lang.Specification

class ScopesNotGlobalTest extends Specification {

    ScopesFail scopes
    ScopesFailNoPrint scopesFailNoPrint

    void setup() {
        scopes = new ScopesFail()
        scopesFailNoPrint = new ScopesFailNoPrint()
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

    def 'Should not get local value globally via function after running script'() {
        given:
        scopes
        when:
        scopesFailNoPrint.run()
        scopes.fLocal()
        def local = scopes.q
        then:
        final MissingPropertyException exception = thrown()
        assert exception != null
    }
}