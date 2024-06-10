package com.baeldung.deepshallow;

public class Person implements Cloneable {
    private String name;
    private Contact contact;

    public Person(String name, Contact contact){
        this.name = name;
        this.contact = contact;
    }

    public Person(Person person){
        this.name = person.name;
        this.contact = person.contact;
    }

    public Person(){}

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public static Person deepCopy(Person person){
        Person person1 = new Person();
        person1.setContact(new Contact("City"));
        person1.setName(person.getName());

        return person1;
    }

    @Override
    public Person clone(){
        try{
            return (Person)super.clone();
        } catch(CloneNotSupportedException ex){
            throw new AssertionError();
        }
    }

    public Person deepClone() {
        try{
            Person nPerson = (Person)super.clone();
        
            nPerson.setContact((Contact)contact.clone());

            return nPerson;
        } catch(CloneNotSupportedException ex){
            throw new AssertionError();
        }
    }
}
