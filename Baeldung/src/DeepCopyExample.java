public class DeepCopyExample {

    public static void main(String[] args) {
        // Create an original person
        Person originalPerson = new Person("John", 30, 170, 70, new Address("Deep Copy Main St.", "California", "New York"));

        // Perform deep copy
        Person copiedPerson = originalPerson.deepClone();

        // Modify the copied person's properties (mutable properties)
        copiedPerson.setAge(35);
        copiedPerson.setName("Jane");
        copiedPerson.setHeight(180); // in cm
        copiedPerson.setWeight(75); // in kg

        // Output original person's properties
        System.out.println("Original Person's Age: " + originalPerson.getAge());
        System.out.println("Original Person's Name: " + originalPerson.getName());
        System.out.println("Original Person's Height: " + originalPerson.getHeight() + " cm");
        System.out.println("Original Person's Weight: " + originalPerson.getWeight() + " kg");
    }
}
