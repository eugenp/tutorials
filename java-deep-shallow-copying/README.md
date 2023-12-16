## Understanding Deep vs Shallow Copying in Java

This project explores the concept of deep and shallow copying in Java and provides insights into their differences and best practices.

### Overview

This Java project aims to elucidate the distinction between deep and shallow copying and their implications in programming. It includes code samples, explanations, and practical examples to showcase how these concepts operate within Java.

### Building the Project

This is a Maven project. To build it, follow these steps:

1. Clone the repository to your local machine:

   ```shell
   git clone <repository_url>

2. Navigate to the project directory

    ```shell
   cd java-deep-shallow-copying
   ```

3. Build the project using Maven

    ```shell
    mvn clean install
   ```
### Running the Project

#### Deep Copying Demo

- To run the Deep Copying Demo, execute the following command

    ```shell
    java -cp target/classes com.baeldung.copying.deep.DeepCopyingDemo
    ```
In alternative, you can run it using your IDE ```> Run >  DeepCopyingDemo.main()```

#### Shallow Copying Demo
- To run the Shallow Copying Demo, use the following command:
  
- ```shell
    java -cp target/classes com.baeldung.copying.deep.DeepCopyingDemo
    ```
In alternative, you can run it using your IDE ```> Run > ShallowCopyingDemo.main()```


### Running tests
- To execute tests for this project, use the following command:

    ```shell
    mvn test
    ```
This command runs all the test cases defined in the project to ensure the functionalities and behaviors are as expected.
### Conclusion
Understanding the **differences between deep and shallow copying** is crucial in Java programming, especially **when dealing with complex data structures and object manipulation**. 

This project aims to provide a comprehensive understanding of these concepts and their implications, offering insights that can help in writing more robust and efficient Java applications.

### Relevant Articles:

- [Understanding Deep vs Shallow Copying in Java](https://drafts.baeldung.com/?p=185079&preview=true)
