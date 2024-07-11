# README

## Shallow and Deep Copy Examples in Java

This repository contains Java examples demonstrating the concepts of shallow copy and deep copy using custom `Person` and `Address` classes.

### Contents

1. **ShallowCopyExample.java**
    - Demonstrates shallow copying where the copied object shares references with the original object.
2. **DeepCopyExample.java**
    - Demonstrates deep copying where the copied object has its own separate copy of the referenced objects.
3. **Address.java** and **Person.java**
    - Contain the implementations of the `Address` and `Person` classes with methods for shallow and deep copying.

### Classes Overview

#### Address Class

```java
package com.baeldung;

class Address {
    String city;

    Address(String city) {
        this.city = city;
    }

    // Method to create a deep copy of Address
    Address deepCopy() {
        return new Address(this.city);
    }
}
```

The `Address` class represents an address with a single attribute `city`. It includes a `deepCopy` method to create a deep copy of an `Address` object.

#### Person Class

```java
package com.baeldung;

class Person {
    String name;
    Address address;

    Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    // Method to create a deep copy of Person
    Person deepCopy() {
        return new Person(this.name, this.address.deepCopy());
    }

    // Method to create a shallow copy
    Person shallowCopy() {
        return new Person(this.name, this.address);
    }
}
```

The `Person` class represents a person with a `name` and an `address`. It includes methods to create shallow and deep copies of a `Person` object.

### ShallowCopyExample

The `ShallowCopyExample` class demonstrates shallow copying:

```java
public class ShallowCopyExample {
    public static void main(String[] args) {
        Address address = new Address("Glasgow");
        Person person1 = new Person("John", address);
        Person person2 = person1.shallowCopy();

        // Modifying the city of person2's address
        person2.address.city = "London";

        // Modifying person2's name
        person2.name = "Scott";

        // Both person1 and person2 share the same address object
        System.out.println(person1.address.city);  // Output: London
        System.out.println(person1.name);  // Output: John
        System.out.println(person2.name);  // Output: Scott
    }
}
```

In this example, modifying `person2.address.city` affects `person1.address.city` because they share the same `Address` object due to shallow copying.

### DeepCopyExample

The `DeepCopyExample` class demonstrates deep copying:

```java
package com.baeldung;

public class DeepCopyExample {
    public static void main(String[] args) {
        Address address = new Address("Glasgow");
        Person person1 = new Person("John", address);
        Person person2 = person1.deepCopy();

        // Modifying the city of person2's address
        person2.address.city = "London";

        // Both person1 and person2 have separate address objects
        System.out.println(person1.address.city);  // Output: Glasgow
        System.out.println(person2.address.city);  // Output: London
    }
}
```

In this example, modifying `person2.address.city` does not affect `person1.address.city` because they have separate `Address` objects due to deep copying.

### Usage

To run these examples, compile and run the respective Java files using your preferred IDE or command line.

For example, using the command line:

```bash
javac ShallowCopyExample.java
java ShallowCopyExample

javac -d . DeepCopyExample.java
java com.baeldung.DeepCopyExample
```

### Conclusion

This repository provides a clear demonstration of the differences between shallow and deep copying in Java. By examining the behavior of the `ShallowCopyExample` and `DeepCopyExample` classes, you can understand how object references are handled in each case.