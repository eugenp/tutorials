package com.baeldung.keyword.thiskeyword;

public class KeywordUnitTest {

    private String name;
    private int age;

    public KeywordUnitTest() {
        this("John", 27);
        this.printMessage();
        printInstance(this);
    }

    public KeywordUnitTest(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void printMessage() {
        System.out.println("invoked by this");
    }

    public void printInstance(KeywordUnitTest thisKeyword) {
        System.out.println(thisKeyword);
    }

    public KeywordUnitTest getCurrentInstance() {
        return this;
    }

    class ThisInnerClass {

        boolean isInnerClass = true;

        public ThisInnerClass() {
            KeywordUnitTest thisKeyword = KeywordUnitTest.this;
            String outerString = KeywordUnitTest.this.name;
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
