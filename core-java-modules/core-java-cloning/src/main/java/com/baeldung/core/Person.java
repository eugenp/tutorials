package core;

class Person implements Cloneable {
    String name;
    int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        Person clonedPerson = (Person) super.clone();
        return clonedPerson;
    }
}