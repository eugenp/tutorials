package com.baeldung.metaprogramming.extension

import com.baeldung.metaprogramming.Employee

class StaticEmployeeExtension {
    
    static Employee getDefaultObj(Employee self) {
        return new Employee(firstName: "firstName", lastName: "lastName", age: 20)
    }
}