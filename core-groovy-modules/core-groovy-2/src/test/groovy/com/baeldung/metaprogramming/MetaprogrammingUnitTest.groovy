package com.baeldung.metaprogramming

import spock.lang.Specification

import java.time.Year

class MetaprogrammingUnitTest extends Specification {

    Employee emp

    void setup() {
        emp = new Employee(firstName: "Norman", lastName: "Lewis")
    }

    def "testPropertyMissing"() {
        expect:
        emp.address == "property 'address' is not available"
    }

    def "testMethodMissing"() {
        given:
        Employee emp = new Employee()

        expect:
        emp.getFullName() == "method 'getFullName' is not defined"
    }

    def "testMetaClassProperty"() {
        when:
        Employee.metaClass.address = ""

        and:
        emp = new Employee(firstName: "Norman",
                           lastName: "Lewis",
                           address: "US")

        then:
        emp.address == "US"
    }

    def "testMetaClassMethod"() {
        when:
        emp.metaClass.getFullName = {
            "$lastName, $firstName"
        }

        then:
        emp.getFullName() == "Lewis, Norman"
    }

    def "testOnlyNameConstructor"() {
        when:
        new Employee("Norman")

        then:
        thrown(GroovyRuntimeException)
    }

    def "testMetaClassConstructor"() {
        when:
        Employee.metaClass.constructor = { String firstName ->
            new Employee(firstName: firstName)
        }

        and:
        Employee norman = new Employee("Norman")

        then:
        norman.firstName == "Norman"
        norman.lastName == null
    }

    def "testJavaMetaClass"() {
        when:
        String.metaClass.capitalize = { String str ->
            str.substring(0, 1).toUpperCase() + str.substring(1)
        }

        and:
        String.metaClass.static.joinWith = { String delimiter, String... args ->
            String.join(delimiter, args)
        }

        then:
        "norman".capitalize() == "Norman"
        String.joinWith(" -> ", "a", "b", "c") == "a -> b -> c"
    }

    def "testEmployeeExtension"() {
        given:
        def age = 28
        def expectedYearOfBirth = Year.now() - age
        Employee emp = new Employee(age: age)

        expect:
        emp.getYearOfBirth() == expectedYearOfBirth.value
    }

    def "testJavaClassesExtensions"() {
        when:
        5.printCounter()

        then:
        40L.square() == 1600L
        (2.98).cube() == 26.463592
    }

    def "testStaticEmployeeExtension"() {
        assert Employee.getDefaultObj().firstName == "firstName"
        assert Employee.getDefaultObj().lastName == "lastName"
        assert Employee.getDefaultObj().age == 20
    }

    def "testToStringAnnotation"() {
        when:
        Employee employee = new Employee().tap {
            id = 1
            firstName = "norman"
            lastName = "lewis"
            age = 28
        }

        then:
        employee.toString() == "Employee(norman, lewis, 28)"
    }

    def "testTupleConstructorAnnotation"() {
        when:
        Employee norman = new Employee(1, "norman", "lewis", 28)
        Employee snape = new Employee(2, "snape")

        then:
        norman.toString() == "Employee(norman, lewis, 28)"
        snape.toString() == "Employee(snape, null, 0)"
    }

    def "testEqualsAndHashCodeAnnotation"() {
        when:
        Employee norman = new Employee(1, "norman", "lewis", 28)
        Employee normanCopy = new Employee(1, "norman", "lewis", 28)

        then:
        norman == normanCopy
        norman.hashCode() == normanCopy.hashCode()
    }

    def "testAutoCloneAnnotation"() {
        given:
        Employee norman = new Employee(1, "norman", "lewis", 28)

        when:
        def normanCopy = norman.clone()

        then:
        norman == normanCopy
    }

    def "testLoggingAnnotation"() {
        given:
        Employee employee = new Employee(1, "Norman", "Lewis", 28)
        employee.logEmp() // INFO: Employee: Lewis, Norman is of 28 years age
    }
}
