package com.baeldung.metaprogramming

import groovy.time.TimeCategory

class MetaprogrammingUnitTest extends GroovyTestCase {

    Employee emp = new Employee(firstName: "Norman", lastName: "Lewis")

    void testPropertyMissing() {
        assert emp.address == "property 'address' is not available"
    }

    void testMethodMissing() {
        Employee emp = new Employee()
        try {
            emp.getFullName()
        } catch(MissingMethodException e) {
            println "method is not defined"
        }
        assert emp.getFullName() == "method 'getFullName' is not defined"
    }

    void testMetaClassProperty() {
        Employee.metaClass.address = ""
        emp = new Employee(firstName: "Norman", lastName: "Lewis", address: "US")
        assert emp.address == "US"
    }

    void testMetaClassMethod() {
        emp.metaClass.getFullName = {
            "$lastName, $firstName"
        }
        assert emp.getFullName() == "Lewis, Norman"
    }

    void testMetaClassConstructor() {
        try {
            Employee emp = new Employee("Norman")
        } catch(GroovyRuntimeException e) {
            assert e.message == "Could not find matching constructor for: com.baeldung.metaprogramming.Employee(String)"
        }

        Employee.metaClass.constructor = { String firstName ->
            new Employee(firstName: firstName)
        }

        Employee norman = new Employee("Norman")
        assert norman.firstName == "Norman"
        assert norman.lastName == null
    }

    void testJavaMetaClass() {
        String.metaClass.capitalize = { String str ->
            str.substring(0, 1).toUpperCase() + str.substring(1);
        }
        assert "norman".capitalize() == "Norman"
    }

    void testEmployeeExtension() {
        Employee emp = new Employee(age: 28)
        assert emp.getYearOfBirth() == 1992
    }

    void testJavaClassesExtensions() {
        5.printCounter()

        assert 40l.square() == 1600l

        assert (2.98).cube() == 26.463592
    }

    void testStaticEmployeeExtension() {
        assert Employee.getDefaultObj().firstName == "firstName"
        assert Employee.getDefaultObj().lastName == "lastName"
        assert Employee.getDefaultObj().age == 20
    }

    void testToStringAnnotation() {
        Employee employee = new Employee()
        employee.id = 1
        employee.firstName = "norman"
        employee.lastName = "lewis"
        employee.age = 28

        assert employee.toString() == "Employee(norman, lewis, 28)"
    }

    void testTupleConstructorAnnotation() {
        Employee norman = new Employee(1, "norman", "lewis", 28)
        assert norman.toString() == "Employee(norman, lewis, 28)"

        Employee snape = new Employee(2, "snape")
        assert snape.toString() == "Employee(snape, null, 0)"

    }
    
    void testEqualsAndHashCodeAnnotation() {
        Employee norman = new Employee(1, "norman", "lewis", 28)
        Employee normanCopy = new Employee(1, "norman", "lewis", 28)
        assert norman.equals(normanCopy)
        assert norman.hashCode() == normanCopy.hashCode()
    }
    
    void testAutoCloneAnnotation() {
        try {
            Employee norman = new Employee(1, "norman", "lewis", 28)
            def normanCopy = norman.clone()
            assert norman == normanCopy
        } catch(CloneNotSupportedException e) {
            e.printStackTrace()
        }
    }

    void testLoggingAnnotation() {
        Employee employee = new Employee(1, "Norman", "Lewis", 28)
        employee.logEmp()
    }
}
