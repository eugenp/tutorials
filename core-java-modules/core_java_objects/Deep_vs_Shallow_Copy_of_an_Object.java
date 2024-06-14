public class Address {
    String city;

    public Address(String city) {
        this.city = city;
    }
}

public class Person implements Cloneable {
    String name;
    Address address;

    public Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // Deep copy implementation
        Person cloned = (Person) super.clone();
        cloned.address = new Address(this.address.city);
        return cloned;
    }

    // Uncomment the following for shallow copy implementation
    /*
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    */

    public static void main(String[] args) throws CloneNotSupportedException {
        // Shallow copy demonstration
        /*
        Address address = new Address("Mumbai");
        Person person1 = new Person("Patel", address);
        Person person2 = (Person) person1.clone();

        System.out.println(person1.address.city); // Mumbai
        System.out.println(person2.address.city); // Mumbai

        person2.address.city = "Bengaluru";
        System.out.println(person1.address.city); // Bengaluru
        System.out.println(person2.address.city); // Bengaluru
        */

        // Deep copy demonstration
        Address address = new Address("Mumbai");
        Person person1 = new Person("Patel", address);
        Person person2 = (Person) person1.clone();

        System.out.println(person1.address.city); // Mumbai
        System.out.println(person2.address.city); // Mumbai

        person2.address.city = "Bengaluru";
        System.out.println(person1.address.city); // Mumbai
        System.out.println(person2.address.city); // Bengaluru
    }
}
