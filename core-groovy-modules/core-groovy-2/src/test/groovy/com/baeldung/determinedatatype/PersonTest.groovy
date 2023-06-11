package com.baeldung.determinedatatype

import spock.lang.Specification

class PersonTest extends Specification {

    def "givenWhenParameterTypeIsIntegerThenReturnTrue"() {
        given:
        Person personObj = new Person(10)

        expect:
        personObj.ageAsInt.class == Integer
    }

    def "givenWhenParameterTypeIsDouble_thenReturnTrue"() {
        given:
        Person personObj = new Person(10.0)

        expect:
        personObj.ageAsDouble.class == Double
    }

    def "givenWhenParameterTypeIsString_thenReturnTrue"() {
        given:
        Person personObj = new Person("10 years")

        expect:
        personObj.ageAsString.class == String
    }

    def "givenClassName_WhenParameterIsInteger_thenReturnTrue"() {
        expect:
        Person.class.getDeclaredField('ageAsInt').type == int.class
    }

    def "givenWhenObjectIsInstanceOfType_thenReturnTrue"() {
        given:
        Person personObj = new Person()

        expect:
        personObj.class == Person
    }

    def "givenWhenInstanceIsOfSubtype_thenReturnTrue"() {
        given:
        Student studentObj = new Student()

        expect:
        studentObj.class.superclass == Person
    }

    def "givenGroovyList_WhenFindClassName_thenReturnTrue"() {
        given:
        def ageList = ['ageAsString', 'ageAsDouble', 10]

        expect:
        ageList.class == ArrayList
        ageList.getClass() == ArrayList
    }

    def "givenGroovyMap_WhenFindClassName_thenReturnTrue"() {
        given:
        def ageMap = [ageAsString: '10 years', ageAsDouble: 10.0]

        expect:
        ageMap.getClass() == LinkedHashMap
    }
}
