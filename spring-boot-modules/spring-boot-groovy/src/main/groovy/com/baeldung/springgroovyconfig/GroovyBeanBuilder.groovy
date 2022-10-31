package com.baeldung.springgroovyconfig


beans {

    // Declares a simple bean with a constructor argument
    companyBean(Company, name: 'ABC Inc');

    // The same bean can be declared using a simpler syntax: beanName(type, constructor-args)
    company String, 'XYZ Inc'

    // Declares an employee object with setters referencing the previous bean
    employee(Employee) {
        firstName = 'Lakshmi'
        lastName = 'Priya'

        // References to other beans can be done in both the ways
        company = company // or vendor = ref('company')
    }

    // Allows import of other configuration files, both XML and Groovy
    importBeans('classpath:xml-bean-config.xml')
}
