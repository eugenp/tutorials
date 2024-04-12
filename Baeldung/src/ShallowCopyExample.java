public class ShallowCopyExample {
    public static void main(String[] args) {
        try {

            Person originalPerson = new Person("John", 30,18,30,new Address("Shallow Copy Main St.", "Shallow Copy Texas",  "USA"));


            Person copiedPerson = originalPerson.clone();


            copiedPerson.setAge(35);


            System.out.println("Original Person's Age: " + originalPerson.getAge());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
