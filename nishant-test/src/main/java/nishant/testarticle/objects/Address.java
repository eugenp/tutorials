package nishant.testarticle.objects;

import java.io.Serializable;

public class Address implements Cloneable, Serializable {

    private String houseNumber;
    private String lane;
    private String city;

    public Address(String houseNumber, String lane, String city) {
        this.houseNumber = houseNumber;
        this.lane = lane;
        this.city = city;
    }

    public Address(Address address) {
        this(address.getHouseNumber(), address.getLane(), address.getCity());
    }

    @Override
    protected Address clone() throws CloneNotSupportedException {
        return (Address) super.clone();
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getLane() {
        return lane;
    }

    public void setLane(String lane) {
        this.lane = lane;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
