class Address {
    String city;

    public Address(String city) {
        this.city = city;
    }
}


class PersonShallow {
    String name;
    Address address;

    
    public PersonShallow(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    
    public PersonShallow(PersonShallow other) {
        this.name = other.name;
        this.address = other.address; 
    }
}


class PersonDeep {
    String name;
    Address address;

    
    public PersonDeep(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    
    public PersonDeep(PersonDeep other) {
        this.name = other.name;
        this.address = new Address(other.address.city); 
    }
}

