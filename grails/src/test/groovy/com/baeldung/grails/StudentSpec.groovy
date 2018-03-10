package com.baeldung.grails

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class StudentSpec extends Specification implements DomainUnitTest<Student> {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
