package org.hibernate.caveatemptor.tutorial3.auction.model;

import javax.persistence.*;
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
@org.hibernate.annotations.GenericGenerator(
    name = "userAddressSharedPKGenerator",
    strategy ="foreign",
    parameters = @org.hibernate.annotations.Parameter(name = "property", value = "user")
)

@Entity
@Table(name = "ADDRESS")
public class AddressEntity implements Serializable {

    @Id @GeneratedValue(generator = "userAddressSharedPKGenerator")
    @Column(name = "ADDRESS_ID")
    // TODO: This is ignored: @org.hibernate.annotations.ForeignKey(name = "FK_ADDRESS_USERS_SHARED_PK")
    private Long id = null;

    @Version
    @Column(name = "OBJ_VERSION")
    private int version = 0;

    @Column(name = "STREET", length = 255, nullable = false)
    private String street;

    @Column(name = "ZIPCODE", length = 16, nullable = false)
    private String zipcode;

    @Column(name = "CITY", length = 255, nullable = false)
    private String city;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
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
