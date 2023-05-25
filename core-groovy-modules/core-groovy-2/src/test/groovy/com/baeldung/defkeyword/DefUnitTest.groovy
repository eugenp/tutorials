package com.baeldung.defkeyword

import org.codehaus.groovy.runtime.NullObject
import org.codehaus.groovy.runtime.typehandling.GroovyCastException

import groovy.transform.TypeChecked
import groovy.transform.TypeCheckingMode

@TypeChecked
class DefUnitTest extends GroovyTestCase {
    
    def id
    def firstName = "Samwell"
    def listOfCountries = ['USA', 'UK', 'FRANCE', 'INDIA']
    
    @TypeChecked(TypeCheckingMode.SKIP)
    def multiply(x, y) {
        return x*y
    }
        
    @TypeChecked(TypeCheckingMode.SKIP)
    void testDefVariableDeclaration() {
        
        def list
        assert list.getClass() == org.codehaus.groovy.runtime.NullObject
        assert list.is(null)
        
        list = [1,2,4]
        assert list instanceof ArrayList
    }
    
    @TypeChecked(TypeCheckingMode.SKIP)
    void testTypeVariables() {   
        int rate = 200
        try {
            rate = [12] //GroovyCastException
            rate = "nill" //GroovyCastException
        } catch(GroovyCastException) {
            println "Cannot assign anything other than integer"
        }
    }
     
    @TypeChecked(TypeCheckingMode.SKIP)
    void testDefVariableMultipleAssignment() {
        def rate
        assert rate == null
        assert rate.getClass() == org.codehaus.groovy.runtime.NullObject
        
        rate = 12
        assert rate instanceof Integer
        
        rate = "Not Available"
        assert rate instanceof String
        
        rate = [1, 4]
        assert rate instanceof List
        
        assert divide(12, 3) instanceof BigDecimal
        assert divide(1, 0) instanceof String

    }
    
    def divide(int x, int y) {
        if(y==0) {
            return "Should not divide by 0"
        } else {
            return x/y
        }
    }
    
    def greetMsg() {
        println "Hello! I am Groovy"
    }
    
    void testDefVsType() {
        def int count
        assert count instanceof Integer
    }
}