package com.baeldung.boot.passenger;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
class Passenger {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(nullable = false)
    private String firstName;

    @Basic(optional = false)
    @Column(nullable = false)
    private String lastName;

    @Basic(optional = false)
    @Column(nullable = false)
    private Integer seatNumber;

    private Passenger(String firstName, String lastName, Integer seatNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.seatNumber = seatNumber;
    }

    static Passenger from(String firstName, String lastName, Integer seatNumber) {
        return new Passenger(firstName, lastName, seatNumber);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        Passenger passenger = (Passenger) object;
        return getSeatNumber() == passenger.getSeatNumber() && Objects.equals(getFirstName(), passenger.getFirstName())
          && Objects.equals(getLastName(), passenger.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getSeatNumber());
    }

    @Override
    public String toString() {
        final StringBuilder toStringBuilder = new StringBuilder(getClass().getSimpleName());
        toStringBuilder.append("{ id=").append(id);
        toStringBuilder.append(", firstName='").append(firstName).append('\'');
        toStringBuilder.append(", lastName='").append(lastName).append('\'');
        toStringBuilder.append(", seatNumber=").append(seatNumber);
        toStringBuilder.append('}');
        return toStringBuilder.toString();
    }

    Long getId() {
        return id;
    }

    String getFirstName() {
        return firstName;
    }

    String getLastName() {
        return lastName;
    }

    Integer getSeatNumber() {
        return seatNumber;
    }
}
