package com.baeldung.objectclassguide;

import java.util.Objects;


/**
 * Represents a car object, implementing Cloneable and overriding Object methods.
 *
 * The class uses standard implementation for equals(), hashCode(), and toString().
 * The clone() method performs a shallow copy, which is sufficient since 'make' (String)
 * and 'year' (int) are immutable or primitive.
 */
public class Car implements Cloneable {
    private String make;
    private int year;


    public Car(String make, int year) {
        this.make = make;
        this.year = year;
    }


    // Getters for external access (useful for testing)
    public String getMake() {
        return make;
    }


    public int getYear() {
        return year;
    }


    /**
     * Standard implementation of equals() for value equality.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Car car = (Car) obj;
        // Use Objects.equals for safe String comparison
        return year == car.year && Objects.equals(make, car.make);
    }


    /**
     * Standard implementation of hashCode() based on make and year.
     */
    @Override
    public int hashCode() {
        return Objects.hash(make, year);
    }


    /**
     * Standard implementation of toString() for debugging and logging.
     */
    @Override
    public String toString() {
        return "Car{" +
               "make='" + make + '\'' +
               ", year=" + year +
               '}';
    }


    /**
     * Overrides the protected clone() method from Object to perform a shallow copy.
     * This is the standard pattern when implementing the Cloneable marker interface.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        // Calls Object's native clone() method
        return super.clone();
    }
}
