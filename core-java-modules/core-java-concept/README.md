# Shallow and Deep Copy in Java

## Overview

In object-oriented programming with Java, effectively working with complex object structures often involves copying objects. Understanding the nuances of shallow and deep copy is crucial to achieving the desired behavior and avoiding unintended side effects. This tutorial delves into implementing both approaches and explores best practices.

## Shallow Copy

A shallow copy duplicates the top-level structure of an object, copying the values of its fields. However, references to other objects are simply copied, meaning both the original and the copy point to the same underlying objects. This can lead to unexpected behavior if you modify the referenced objects through the copy.

### Example Implementation

The default implementation of the `clone()` method in the `Object` class performs a shallow copy.

```java
class Address {
    String city;

    public Address(String city) {
        this.city = city;
    }
}

class Person implements Cloneable {
    String name;
    Address address;

    public Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
