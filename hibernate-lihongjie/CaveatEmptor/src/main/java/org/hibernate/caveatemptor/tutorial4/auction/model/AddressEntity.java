package org.hibernate.caveatemptor.tutorial4.auction.model;

import java.io.Serializable;


/**
 * The address of a User.
 *
 * This entity class supports shared references, e.g. the
 * same instance can be referenced by a User (shippingAddress)
 * and by a Shipment (deliveryAddress).
 *
 * @see User
 * @see Shipment
 * @author Christian Bauer
 */
public class AddressEntity implements Serializable {

    private Long id = null;
    private int version = 0;

    private String street;
    private String zipcode;
    private String city;

    private User user;

    /**
     * No-arg constructor for JavaBean tools
     */
    public AddressEntity() {}

    /**
     * Full constructor
     */
    public AddressEntity(String street, String zipcode, String city) {
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
    }

    // ********************** Accessor Methods ********************** //

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getVersion() { return version; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getZipcode() { return zipcode; }
    public void setZipcode(String zipcode) { this.zipcode = zipcode; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    // ********************** Common Methods ********************** //

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressEntity)) return false;

        final AddressEntity address = (AddressEntity) o;

        if (!city.equals(address.city)) return false;
        if (!street.equals(address.street)) return false;
        return zipcode.equals(address.zipcode);

    }

    public int hashCode() {
        int result;
        result = street.hashCode();
        result = 29 * result + zipcode.hashCode();
        result = 29 * result + city.hashCode();
        return result;
    }

    public String toString() {
        return  "Street: '" + getStreet() + "', " +
                "Zipcode: '" + getZipcode() + "', " +
                "City: '" + getCity() + "'";
    }

    // ********************** Business Methods ********************** //

}
