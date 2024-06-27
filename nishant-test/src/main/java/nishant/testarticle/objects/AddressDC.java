package nishant.testarticle.objects;

import java.io.Serializable;

/**
 * A Deep copy implementation of Address Class.
 * Address DeepCopy is abbreviated to AddressDC.
 */
public class AddressDC implements Cloneable, Serializable {

    private String houseNumber;
    private String lane;
    private String city;

    public AddressDC(AddressDC address) {
        this(address.getHouseNumber(), address.getLane(), address.getCity());
    }

    @Override
    protected AddressDC clone() throws CloneNotSupportedException {
        return (AddressDC) super.clone();
    }

    public AddressDC(String houseNumber, String lane, String city) {
        this.houseNumber = houseNumber;
        this.lane = lane;
        this.city = city;
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
