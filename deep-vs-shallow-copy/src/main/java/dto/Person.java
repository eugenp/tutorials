package dto;

public class Person implements Cloneable {

    public String name;
    public Car car;

    public Person(String name, Car car) {
        this.name = name;
        this.car = car;
    }

    @Override
    public Person clone() {
        try {
            return (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
