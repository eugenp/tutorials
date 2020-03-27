package com.baeldung.java.patterns;

/**
 *
 * The Interface that acts as a Driven Port (database) of the Hexagonal Architecture
 * @author Ganapathy Kumar
 */
public interface LibraryRepositoryPort {

    void subscribe(String emailAddress);

    void unSubscribe(String emailAddress);

}
