package com.baeldung.deepshallowcopy.manual;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student {

    private String name;
    private String batch;
    private String department;
    private Address address;

    public Student studentShallowCopy() {

        return new Student(this.name,this.batch,this.department,this.address);
    }

    public Student studentDeepCopy() {

        Address newAddress = new Address(this.address.getStreet(), this.address.getCity(),this.address.getState());
        return new Student(this.name,this.batch,this.department,newAddress);
    }
}
