package nishant.testarticle.objects;

import java.io.Serializable;

/**
 * A Deep copy implementation of Person Class.
 * Person DeepCopy is abbreviated to PersonDC.
 */
public class PersonDC implements Cloneable, Serializable {

    private String name;
    private int age;
    private AddressDC address;

    @Override
    public PersonDC clone() throws CloneNotSupportedException {
        PersonDC copiedPerson = (PersonDC) super.clone();
        copiedPerson.setAddress(new AddressDC(this.getAddress()));
        return copiedPerson;
    }

    public PersonDC(String name, int age, AddressDC address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public PersonDC(PersonDC sourcePerson) {
        name = sourcePerson.getName();
        age = sourcePerson.getAge();
        address = new AddressDC(sourcePerson.getAddress());
    }

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

    public AddressDC getAddress() {
        return address;
    }

    public void setAddress(AddressDC address) {
        this.address = address;
    }
}
