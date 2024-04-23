# java-object-copier-demo

Shallow Copy vs. Deep Copy in Java
This project provides a detailed explanation and practical examples to distinguish between shallow and deep copying in Java.

# Understanding the Concepts

## Shallow Copy

A shallow copy creates a new object instance and copies the values of the original object's fields into it.
For primitive fields, values are copied directly.
For reference type fields (objects), only the references are copied, meaning both the original and the copied object point to the same data in memory.

## Deep Copy:

A deep copy creates a fully independent copy of the original object and all its nested objects.
This involves recursively copying all referenced objects, creating a completely new object structure in memory.

# Running the Examples

* Compile the code: javac *.java
* Run the Tests:
  * Demonstrates Shallow copy
    ```OrderCopyTest.shouldModifyOriginalOrderWhenShallowCopyingOrder```
  * Demonstrates Deep copy
    ```OrderCopyTest.shouldRetainingOriginalOrderWhenDeepCopyingOrder```

# Thank you!