package com.baeldung.generics;

class Cat extends Animal implements Comparable<Cat> {
    public Cat(String type, String name) {
        super(type, name);
    }

    @Override
    public String makeSound() {
        return "Meow";
    }

    @Override
    public int compareTo(Cat cat) {
        return this.getName()
            .length() - cat.getName()
            .length();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cat{");
        sb.append("type='")
            .append(type)
            .append('\'');
        sb.append(", name='")
            .append(name)
            .append('\'');
        sb.append('}');
        return sb.toString();
    }
}
