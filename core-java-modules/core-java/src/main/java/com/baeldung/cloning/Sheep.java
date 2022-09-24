package com.baeldung.cloning;

import java.util.Objects;

public class Sheep implements Cloneable {
    
    int age;
    
    Herder herder;

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    protected Sheep deepCopy() throws CloneNotSupportedException {
        Sheep newSheep = (Sheep) super.clone();
        if (herder != null) {
            newSheep.herder = (Herder) herder.clone();
        }
        return newSheep;
    }
    
    public Sheep(int age, Herder herder) {
        super();
        this.age = age;
        this.herder = herder;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Herder getHerder() {
        return herder;
    }

    public void setHerder(Herder herder) {
        this.herder = herder;
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, herder);
    }
}
