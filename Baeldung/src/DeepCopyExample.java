public class DeepCopyExample {

    public static void main(String[] args) {
        Person originalPerson = new Person("John", 30);
        Person copiedPerson = originalPerson.deepClone();

        System.out.println("Original Person's Age: " + originalPerson.getAge());
    }
}
