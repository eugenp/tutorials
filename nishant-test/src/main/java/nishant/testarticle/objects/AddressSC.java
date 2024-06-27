package nishant.testarticle.objects;

import java.io.Serializable;

/**
 * A shallow copy implementation of Address Class.
 * Address ShallowCopy is abbreviated to AddressSC.
 */
public class AddressSC implements Cloneable {

    private String houseNumber;
    private String lane;
    private String city;

    public AddressSC(String houseNumber, String lane, String city) {
        this.houseNumber = houseNumber;
        this.lane = lane;
        this.city = city;
    }

    @Override
    protected AddressSC clone() throws CloneNotSupportedException {
        return (AddressSC) super.clone();
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
