package com.baeldung.java8.comparator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
class Employee implements Comparable<Employee>{
    private String name;
    int age;
    double salary;
    long mobile;
    
    @Override
    public int compareTo(Employee argEmployee) {
        return name.compareTo(argEmployee.getName());
    }
    
}
