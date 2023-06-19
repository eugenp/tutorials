package com.baeldung.cloning.domain;

public class Person implements Cloneable {

    private String name;
    private Integer age;
    private Boolean married;

    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Person(String name, Integer age, Boolean married) {
        this.name = name;
        this.age = age;
        this.married = married;
    }

    public String getName() {
        return this.name;
    }

    public Integer getAge() {
        return this.age;
    }

    public Boolean isMarried() {
        return this.married;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setMarried(Boolean married) {
        this.married = married;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * creates shallow clone of the person
     * @return shallow copy of person obj
     */
    public Person createShallowClone() throws CloneNotSupportedException {

        return this.clone();
    }

    /**
     * creates deep clone of the person
       * @return deep copy of person obj
     */
    public Person createDeepClone() throws CloneNotSupportedException {

        Person person = this.clone();
        Address address = this.getAddress().clone();
        person.setAddress(address);
        return person;

    }

    @Override
    public Person clone() throws CloneNotSupportedException {
        return (Person)super.clone();
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", married=" + married +
                ", address=" + address +
                '}';
    }
}
