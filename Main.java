public class Main {
    public static void main(String[] args) {
        // Creating an address
        Address address1 = new Address("New York");

        // Creating a person with this address using shallow copy
        PersonShallow personShallow1 = new PersonShallow("John", address1);

        // Creating another person with this address using shallow copy constructor
        PersonShallow personShallow2 = new PersonShallow(personShallow1);

        // Modifying the address of the shallow copied person
        personShallow2.address.city = "Los Angeles";

        // Displaying information of both shallow copied persons
        System.out.println("Shallow Copy:");
        System.out.println("Person 1's name: " + personShallow1.name);
        System.out.println("Person 1's city: " + personShallow1.address.city);
        System.out.println("Person 2's name: " + personShallow2.name);
        System.out.println("Person 2's city: " + personShallow2.address.city);

        // Creating a new address for deep copy
        Address address2 = new Address("New York");

        // Creating a person with this address using deep copy
        PersonDeep personDeep1 = new PersonDeep("John", address2);

        // Creating another person with this address using deep copy constructor
        PersonDeep personDeep2 = new PersonDeep(personDeep1);

        // Modifying the address of the deep copied person
        personDeep2.address.city = "Los Angeles";

        // Displaying information of both deep copied persons
        System.out.println("\nDeep Copy:");
        System.out.println("Person 1's name: " + personDeep1.name);
        System.out.println("Person 1's city: " + personDeep1.address.city);
        System.out.println("Person 2's name: " + personDeep2.name);
        System.out.println("Person 2's city: " + personDeep2.address.city);
    }
}
