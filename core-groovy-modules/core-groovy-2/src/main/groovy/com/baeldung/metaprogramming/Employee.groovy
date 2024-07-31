package com.baeldung.metaprogramming

import groovy.transform.*
import groovy.util.logging.Log

@ToString(includePackage = false, excludes = ['id'])
@TupleConstructor
@EqualsAndHashCode
@Canonical
@AutoClone(style = AutoCloneStyle.SIMPLE)
@Log
class Employee {

    long id
    String firstName
    String lastName
    int age

    //method to catch missing property's getter
    def propertyMissing(String propertyName) {
        log.info "$propertyName is not available"
        "property '$propertyName' is not available"
    }

    //method to catch missing property's setter
    def propertyMissing(String propertyName, propertyValue) {
        println "property '$propertyName' is not available"
        log.info "$propertyName is not available"
        "property '$propertyName' with value '$propertyValue' is not available"
    }

    def methodMissing(String methodName, Object methodArgs) {
        log.info "$methodName is not defined"
        "method '$methodName' is not defined"
    }

    def logEmp() {
        log.info "Employee: $lastName, $firstName is of $age years age"
    }
}
