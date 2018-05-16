package com.baeldung.keyword.thiskeyword;

/**
 * Created by Gebruiker on 5/14/2018.
 */
public class ThisKeyword {

    private String name;
    private int age;

    public ThisKeyword() {
        this("John", 27);
        this.printMessage();
        printInstance(this);
    }

    public ThisKeyword(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void printMessage() {
        System.out.println("invoked by this");
    }

    public void printInstance(ThisKeyword thisKeyword) {
        System.out.println(thisKeyword);
    }

    public ThisKeyword getCurrentInstance() {
        return this;
    }

    class ThisInnerClass {

        boolean isInnerClass = true;

        public ThisInnerClass() {
            ThisKeyword thisKeyword = ThisKeyword.this;
            String outerString = ThisKeyword.this.name;
            System.out.println(this.isInnerClass);
        }
    }

    @Override
    public String toString() {
        return "ThisKeyword{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
