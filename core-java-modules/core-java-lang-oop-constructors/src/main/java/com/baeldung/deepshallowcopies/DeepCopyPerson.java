class DeepCopyPerson extends Person {
    public DeepCopyPerson(String name, Address address) {
        super(name, new Address(address.street, address.number));
    }
}