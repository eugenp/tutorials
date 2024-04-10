public class Person implements Cloneable {

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getter and setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Shallow copy method
    @Override
    public Person clone() throws CloneNotSupportedException {
        return (Person) super.clone();
    }

    // Deep copy method
    public Person deepClone() {
        return new Person(this.name, this.age);
    }
}
