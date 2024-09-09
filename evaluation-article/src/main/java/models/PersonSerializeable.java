package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersonSerializeable implements Serializable {

    private String name;
    private int age;
    private List<PhoneNumber> phoneNumbers;

    public PersonSerializeable(String name, int age, List<PhoneNumber> phoneNumbers) {
        this.name = name;
        this.age = age;
        this.phoneNumbers = phoneNumbers;
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

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }
}

