package com.baeldung.find

import spock.lang.Specification

class SetFindUnitTest extends Specification {

    def "whenSetContainsElement_thenCheckReturnsTrue"() {
        given:
        def set = ['a', 'b', 'c'] as Set

        expect:
        set.contains('a')
        'a' in set
    }
}
