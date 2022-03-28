package com.bealdung.deepcopy;

public class DeepSchoolClone {
    public static void main(String[] args) {
        try {
            deepClone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public static void deepClone() throws CloneNotSupportedException {
        DeepSubject math = new DeepSubject("1", "maths");
        DeepTeacher mike = new DeepTeacher("1", "mike", math);
        DeepTeacher molly;

        System.out.println("before: teacher " + mike.name + " teaches " + mike.subject.name);

        molly = (DeepTeacher) mike.clone();
        molly.setName("kolly");
        molly.setSubject(new DeepSubject("1", "english"));
        System.out.println("teacher: molly " + molly.getName() + " teaches " + molly.getSubject()
            .getName());
        System.out.println("after: teacher " + mike.name + " teaches " + mike.getSubject()
            .getName());
    }
}

class DeepTeacher implements Cloneable {
    String id;
    String name;
    DeepSubject subject;

    public DeepTeacher(String id, String name, DeepSubject subject) {
        this.id = id;
        this.name = name;
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeepSubject getSubject() {
        return subject;
    }

    public void setSubject(DeepSubject subject) {
        this.subject = subject;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        DeepTeacher deepTeacher = (DeepTeacher) super.clone();
        deepTeacher.subject = (DeepSubject) this.subject.clone();
        return super.clone();
    }
}

class DeepSubject implements Cloneable {
    String id;
    String name;

    public DeepSubject(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

