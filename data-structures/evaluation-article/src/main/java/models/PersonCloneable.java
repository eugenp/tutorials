package models;

/**
 * Represents a person with a name and age with cloneable interface.
 */
public class PersonCloneable implements Cloneable {

    private String name;
    private int age;

    public PersonCloneable(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public PersonCloneable clone() throws CloneNotSupportedException {
        return (PersonCloneable) super.clone();
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

