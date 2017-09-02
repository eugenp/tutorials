package com.baeldung.constructordi.dhaval;

public class Lesson {
    public Lesson(String str1, String str2) {
        System.out.println(str1 + " " + str2 + " from Dependency class");
    }

    public void startLearning() {
        System.out.print("We are learning Constructor DI.");
    }
}
