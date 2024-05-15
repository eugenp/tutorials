// Class representing a person using shallow copy approach
class PersonShallow {
    String name;
    Address address;

    // Constructor to initialize the person's name and address
    public PersonShallow(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    // Copy constructor for shallow copy
    public PersonShallow(PersonShallow other) {
        this.name = other.name;
        this.address = other.address; // Shallow copy of the Address object
    }
}

// Class representing a person using deep copy approach
class PersonDeep {
    String name;
    Address address;

    // Constructor to initialize the person's name and address
    public PersonDeep(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    // Copy constructor for deep copy
    public PersonDeep(PersonDeep other) {
        this.name = other.name;
        this.address = new Address(other.address.city); // Deep copy of the Address object
    }
}
