
public class Customer {


    private int id;


    private String name;


    private String customerAddress;

    Customer(String name, String address){
        this.name = name;
        this.customerAddress = address;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCustomerAddress() {
        return this.customerAddress;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCustomerAddress(String address) {
        this.customerAddress = this.name + "," + address;
    }
}
