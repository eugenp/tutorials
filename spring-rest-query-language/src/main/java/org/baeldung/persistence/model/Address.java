package org.baeldung.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "ADDRESS")
public class Address {

    private long id;
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private boolean checked;
    private LocalDate dateOfOccupation;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonBackReference
    private User user;

    @Id
    @GeneratedValue
    @Column(name = "ID")
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "STREET")
    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Column(name = "CITY")
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "STATE")
    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "ZIPCODE")
    public String getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "CHECKED")
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Column(name = "DATE_OF_OCCUPATION")
    public LocalDate getDateOfOccupation() {
        return dateOfOccupation;
    }

    public void setDateOfOccupation(LocalDate dateOfOccupation) {
        this.dateOfOccupation = dateOfOccupation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Address other = (Address) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .add("id", id)
                .add("street", street)
                .add("city", city)
                .add("state", state)
                .add("zipcode", zipcode)
                .toString();
    }

}