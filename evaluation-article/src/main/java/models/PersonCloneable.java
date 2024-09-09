package models;

import java.util.List;

/**
 * Represents a person with a name and age with cloneable interface.
 */
public class PersonCloneable implements Cloneable {

    private String name;
    private int age;
    private List<PhoneNumber> phoneNumbers;

    public PersonCloneable(String name, int age, List<PhoneNumber> phoneNumbers) {
        this.name = name;
        this.age = age;
        this.phoneNumbers = phoneNumbers;
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

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}

