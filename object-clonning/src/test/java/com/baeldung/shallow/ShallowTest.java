package com.baeldung.shallow;

import org.junit.Assert;
import org.junit.Test;

public class ShallowTest {

    @Test
    public void testShallowCloning() throws CloneNotSupportedException {
        Address address = new Address("line1", "line2");
        Employee employee1 = new Employee("Sam", "20", address);
        Employee employee2 = (Employee) employee1.clone();
        employee1.getAddress().setAddressLine2("LINE TWO");
        Assert.assertEquals(employee1.getAddress().getAddressLine2(), employee2.getAddress().getAddressLine2());
    }
}
