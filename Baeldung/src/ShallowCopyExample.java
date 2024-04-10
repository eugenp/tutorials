public class ShallowCopyExample {
    public static void main(String[] args) {
        try {
            Person originalPerson = new Person("John", 30);
            Person copiedPerson = originalPerson.clone();

            copiedPerson.setAge(35);
            System.out.println("Original Person's Age: " + originalPerson.getAge());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

}
