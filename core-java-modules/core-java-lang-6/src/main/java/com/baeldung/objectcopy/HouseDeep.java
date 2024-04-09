package java.com.baeldung.objectcopy;

public class HouseDeep implements Cloneable {

    public String name;
    public String address;
    public GarageDeep garage;

    public HouseDeep(String name, String address, GarageDeep garage) {
        this.name = name;
        this.address = address;
        this.garage = garage;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        HouseDeep house = (HouseDeep) super.clone();
        house.garage = (GarageDeep) garage.clone();
        return house;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public GarageDeep getGarage() {
        return garage;
    }

    public void setGarage(GarageDeep garage) {
        this.garage = garage;
    }
}
