public class Person implements Cloneable {
    private String name;
    private int age;
    private int height; 
    private int weight; 
    private Address address; 

    public Person(String name, int age, int height, int weight, Address address) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.address = address; 
    }

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

    @Override
    public Person clone() throws CloneNotSupportedException {
        return (Person) super.clone();
    }


    public Person deepClone() {
        Address clonedAddress = null;
        if (this.address != null) {
            clonedAddress = new Address(this.address.getStreet(), this.address.getCity(), this.address.getCountry());
        }
        return new Person(this.name, this.age, this.height, this.weight, clonedAddress);
    }
}
