package com.baeldung.scopes

import spock.lang.Specification

class ScopesGlobalTest extends Specification {

    Scopes scopes

    void setup() {
        scopes = new Scopes()
    }

    def 'Should not fail when run'() {
        given:
        scopes
        when:
        scopes.run()
        then:
        noExceptionThrown()
    }

    def 'Should get x value with function while running the script first'() {
        given:
        scopes
        when:
        scopes.run()
        def x = scopes.getGlobalResult()
        then:
        assert x == 201
    }

    def 'Should get x value directly while running the script first'() {
        given:
        scopes
        when:
        scopes.run()
        def x1 = scopes.x
        then:
        assert x1 == 200
    }

    def 'Should get x value with function while not running the script first'() {
        given:
        scopes
        when:
        def x = scopes.getGlobalResult()
        then:
        final MissingPropertyException exception = thrown()
        assert exception != null
    }

    def 'Should get z value with function while running the script first'() {
        given:
        scopes
        when:
        scopes.run()
        def z = scopes.getGlobalCreatedLocally()
        then:
        assert z == 234
    }

    def 'Should get z value directly while running the script first'() {
        given:
        scopes
        when:
        scopes.run()
        def z = scopes.z
        then:
        assert z == 234
    }

    def 'Should get z value directly while not running the script first'() {
        given:
        scopes
        when:
        def z = scopes.z
        then:
        final MissingPropertyException exception = thrown()
        assert exception != null
    }


    def 'Should get z value with function while not running the script first'() {
        given:
        scopes
        when:
        def z = scopes.getGlobalCreatedLocally()
        then:
        assert z == 234
    }
}