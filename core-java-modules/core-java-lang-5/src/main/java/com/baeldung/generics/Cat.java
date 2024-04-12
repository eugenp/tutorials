package com.baeldung.generics;

import java.util.Objects;

class Cat extends Animal implements Comparable<Cat> {
    public Cat(String type, String name) {
        super(type, name);
    }

    @Override
    public String makeSound() {
        return "Meow";
    }

    /**
     * Warning: Inconsistent with the equals method.
     */
    @Override
    public int compareTo(Cat cat) {
        return this.getName().length() - cat.getName().length();
    }

    @Override
    public String toString() {
        return "Cat{" + "type='" + type + '\'' + ", name='" + name + '\'' + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name);
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(!(o instanceof Cat)) return false;
        Cat cat = (Cat) o;
        return type.equals(cat.type) && name.equals(cat.name);
    }

}
