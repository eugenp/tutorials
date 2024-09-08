# Core Java Concepts

This project demonstrates different techniques for performing shallow and deep copies in Java.

It includes examples using the `Cloneable` interface, Apache Commons Lang, Gson, and Jackson libraries.

## Project Structure

- **`Article.java`**: Represents an article with a title and content.
- **`Baeldung.java`**: Represents a website that contains a list of articles.
- **`ArticleTest.java`**: Contains unit tests for different copying methods for the `Article` class.
- **`BaeldungTest.java`**: Contains unit tests for different copying methods for the `Baeldung` class.

## Copying Methods Demonstrated

1. **Cloneable Interface**:
    - **Shallow Copy**: Implements the `Cloneable` interface and overrides the `clone()` method with default implementation. This creates a shallow copy of the object.For our example we did the implementation in Baeldung and Article.java class

2. **Apache Commons Lang**:
    - **Deep Copy**: Uses `SerializationUtils.clone()` from Apache Commons Lang to create a deep copy of the object.

3. **JSON Serialization with Gson**:
    - **Deep Copy**: Uses Gson to serialize and then deserialize the object to create a deep copy.

4. **JSON Serialization with Jackson**:
    - **Deep Copy**: Uses Jackson to serialize and then deserialize the object to create a deep copy.

## Dependencies

The project uses the following dependencies:

- **JUnit 5**: For unit testing.
- **Apache Commons Lang**: For deep copying using `SerializationUtils`.
- **Gson**: For deep copying using JSON serialization.
- **Jackson**: For deep copying using JSON serialization.

## How to Run

1. **Clone the repository**:
    ```bash
    git clone <repo url>
    cd core-java-module
    ```

2. **Build the project**:
    ```bash
    mvn clean install
    ```

3. **Run the tests**:
    ```bash
    mvn test
    ```
