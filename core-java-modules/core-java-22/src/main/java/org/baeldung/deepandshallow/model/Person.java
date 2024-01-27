package org.baeldung.deepandshallow.model;

public class Person implements Cloneable {

    private String name;
    private int age;
    private House house;

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

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    /**
     * Copies all primitives and references
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Person clone() throws CloneNotSupportedException {
        return (Person) super.clone();
    }

    /**
     * Uses the clone method to create a shallow copy then replaces
     * the shared reference with a duplicate object
     * @return
     */
    protected Person deepCopy() {
        Person p = null;
        // Use a copy constructor to create a new house object on the heap
        House h = new House(this.house);
        try {
            p = this.clone();
            p.setHouse(h);
        } catch (CloneNotSupportedException cne) {
            cne.printStackTrace();
        }
        return p;
    }
}
