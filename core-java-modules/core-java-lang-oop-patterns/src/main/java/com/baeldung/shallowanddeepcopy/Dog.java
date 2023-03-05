package com.baeldung.shallowanddeepcopy;

import java.util.UUID;

class Dog {
    private UUID uniqueId;
    private int age;
    private Collar collar;
    private String breed;

    public Dog(UUID uniqueId, int age, Collar collar, String breed) {
        this.uniqueId = uniqueId;
        this.age = age;
        this.collar = collar;
        this.breed = breed;
    }

    public static Dog shallowCopy(Dog dog) {
        return new Dog(dog.getUniqueId(), dog.getAge(), dog.getCollar(), dog.getBreed());
    }

    public static Dog deepCopy(Dog dog) {
        Collar newCollar = new Collar(new String(dog.getCollar()
          .getName()));
        String newBreed = new String(dog.getBreed());
        UUID newId = UUID.fromString(dog.getUniqueId()
          .toString());
        return new Dog(newId, dog.getAge(), newCollar, newBreed);
    }

    public static Dog copy(Dog dog) {
        Collar newCollar = new Collar(dog.getCollar()
          .getName());
        return new Dog(UUID.randomUUID(), dog.getAge(), newCollar, dog.getBreed());
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public int getAge() {
        return age;
    }

    public String getBreed() {
        return breed;
    }

    public Collar getCollar() {
        return collar;
    }

    public void setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCollar(Collar collar) {
        this.collar = collar;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
}
