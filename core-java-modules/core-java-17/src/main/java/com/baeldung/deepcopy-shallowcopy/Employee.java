package com.baeldung.deepcopyshallowcopy;

import java.util.List;

public class Employee {
    public String name;
    public List<String> hobbies;
    public Employee(String name, List<String> hobbies) {
        this.name = name;
        this.hobbies = hobbies; // Reference is copied (shallow copy)
    }

    // copy constructor for shallow copying
    public Employee(Employee shallowCopyEmployee) {
        this.name = shallowCopyEmployee.name;
        this.hobbies = shallowCopyEmployee.hobbies; // only the reference copied
    }
}
