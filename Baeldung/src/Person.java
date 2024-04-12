public class Person implements Cloneable {
    private String name;
    private int age;
    private int height; // in cm
    private int weight; // in kg
    private Address address; // Add address property

    public Person(String name, int age, int height, int weight, Address address) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.address = address; // Initialize address property
    }

    // Getter and setter methods for name, age, height, weight, and address
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    // Shallow copy method
    @Override
    public Person clone() throws CloneNotSupportedException {
        return (Person) super.clone();
    }

    // Deep copy method
    public Person deepClone() {
        // Deep copy of address
        Address clonedAddress = null;
        if (this.address != null) {
            clonedAddress = new Address(this.address.getStreet(), this.address.getCity(), this.address.getCountry());
        }
        return new Person(this.name, this.age, this.height, this.weight, clonedAddress);
    }
}
