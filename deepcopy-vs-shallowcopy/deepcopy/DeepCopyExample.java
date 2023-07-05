public class DeepCopyExample {
    public static void main(String[] args) {
        Person original = new Person("John");
        Person copy = original.deepCopy();
        
        System.out.println(copy.getName());  // Output: John
        
        // Modifying the copy
        copy.setName("Jane");
        System.out.println(copy.getName());  // Output: Jane
        System.out.println(original.getName());  // Output: John (unchanged)
    }
}
