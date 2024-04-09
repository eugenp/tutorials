package java.com.baeldung.objectcopy;

public class HouseShallow implements Cloneable {

    public String name;
    public String address;
    public PostOfficeShallow postOffice;

    public HouseShallow(String name, String address, PostOfficeShallow postOffice) {
        this.name = name;
        this.address = address;
        this.postOffice = postOffice;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
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

    public PostOfficeShallow getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(PostOfficeShallow postOffice) {
        this.postOffice = postOffice;
    }
}
