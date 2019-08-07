package com.baeldung.metaprogramming

import groovy.transform.AutoClone
import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor
import groovy.util.logging.*

@Canonical
@TupleConstructor
@EqualsAndHashCode
@ToString(includePackage=false, excludes=['id'])
@Log 
@AutoClone
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
        "property '$propertyName' is not available"
    }

    def methodMissing(String methodName, def methodArgs) {
        log.info "$methodName is not defined"
        "method '$methodName' is not defined"
    }
    
    def logEmp() {
        log.info "Employee: $lastName, $firstName is of $age years age"
    }

}