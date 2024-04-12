public class DeepCopyExample {

    public static void main(String[] args) {
        
        Person originalPerson = new Person("John", 30, 170, 70, new Address("Deep Copy Main St.", "California", "New York"));

        Person copiedPerson = originalPerson.deepClone();

        copiedPerson.setAge(35);
        copiedPerson.setName("Jane");
        copiedPerson.setHeight(180); 
        copiedPerson.setWeight(75); 


        System.out.println("Original Person's Age: " + originalPerson.getAge());
        System.out.println("Original Person's Name: " + originalPerson.getName());
        System.out.println("Original Person's Height: " + originalPerson.getHeight() + " cm");
        System.out.println("Original Person's Weight: " + originalPerson.getWeight() + " kg");
    }
}
