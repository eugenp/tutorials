package com.bealdung.shallowcopy;

public class ShallowSchoolClone {
    public static void main(String[] args) throws CloneNotSupportedException {

        shallowClone();
    }

    public static void shallowClone() throws CloneNotSupportedException {
        Subject math = new Subject("1", "maths");
        Teacher mike = new Teacher("1", "mike", math);
        Teacher molly;

        System.out.println("before: mike name " + mike.name);
        System.out.println("before: mike teaches " + mike.subject.name);

        molly = (Teacher) mike.clone();
        molly.subject.name = "english";

        molly.name = "polly";
        System.out.println("molly name " + molly.name);
        System.out.println("molly teaches " + molly.subject.name);

        System.out.println("after: mike name " + mike.name);
        System.out.println("after: mike teaches " + mike.subject.name);

    }
}

class Teacher implements Cloneable {
    String id;
    String name;
    Subject subject;

    public Teacher(String id, String name, Subject subject) {
        this.id = id;
        this.name = name;
        this.subject = subject;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}


class Subject {
    String id;
    String name;

    public Subject(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

