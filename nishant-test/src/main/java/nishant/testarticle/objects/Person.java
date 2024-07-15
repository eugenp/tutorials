package nishant.testarticle.objects;

import java.io.Serializable;

public class Person implements Cloneable, Serializable {

    private String name;
    private int age;
    private Address address;

    public Person(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    /**
     * This is a deep clone implementation for the Person class.
     * It can be changed to shallow implementation by following the approach  for shallow mode
     * in below copy constructor.
     *
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public Person clone() throws CloneNotSupportedException {
        Person copiedPerson = (Person) super.clone();
        copiedPerson.setAddress(new Address(this.getAddress()));
        return copiedPerson;
    }

    /**
     * Copy constructor for Person class which can operate in deep or shallow mode based on the flag.
     * @param sourcePerson
     * @param isDeepCopy : whether copy has to be done in a deep mode.
     */
    public Person(Person sourcePerson, boolean isDeepCopy) {
        name = sourcePerson.getName();
        age = sourcePerson.getAge();

        if (isDeepCopy) {
            address = new Address(sourcePerson.getAddress());
        } else {
            address = sourcePerson.getAddress();
        }
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
