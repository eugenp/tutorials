package com.baeldung.keyword.thiskeyword;

public class KeywordTest {

    private String name;
    private int age;

    public KeywordTest() {
        this("John", 27);
        this.printMessage();
        printInstance(this);
    }

    public KeywordTest(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void printMessage() {
        System.out.println("invoked by this");
    }

    public void printInstance(KeywordTest thisKeyword) {
        System.out.println(thisKeyword);
    }

    public KeywordTest getCurrentInstance() {
        return this;
    }

    class ThisInnerClass {

        boolean isInnerClass = true;

        public ThisInnerClass() {
            KeywordTest thisKeyword = KeywordTest.this;
            String outerString = KeywordTest.this.name;
            System.out.println(this.isInnerClass);
        }
    }

    @Override
    public String toString() {
        return "KeywordTest{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
