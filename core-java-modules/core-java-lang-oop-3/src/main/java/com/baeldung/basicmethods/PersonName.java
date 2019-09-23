package com.baeldung.basicmethods;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PersonName {

    public String getName(String firstName, String lastName) throws RuntimeException {
        return firstName + " " + lastName;
    }

    public String getName(String firstName, String middleName, String lastName) {
        if (!middleName.equals("")) {
            return firstName + " " + lastName;
        }
        return firstName + " " + middleName + " " + lastName;
    }

    public void printFullName(String firstName, String lastName) {
        System.out.println(firstName + " " + lastName);
    }

    public void writeName(String name) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter("OutFile.txt"));
        out.println("Name: " + name);
        out.close();
    }

    public static String getNameStatic(String firstName, String lastName) {
        return firstName + " " + lastName;
    }

    public static void callToStaticMethod() {
        System.out.println("Name is: " + PersonName.getNameStatic("Alan", "Turing"));
    }
}
