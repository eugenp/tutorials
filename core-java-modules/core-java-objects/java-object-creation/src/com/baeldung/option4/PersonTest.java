package com.baeldung.option4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PersonTest {

    public static void main(String[] args) {
        Person p1 = new Person("Max Payne", 101);
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream("C:\\Users\\lenovo\\eclipse-workspace\\baeldung\\java-object-creation\\src\\com\\baeldung\\option4\\person_details.txt");
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(p1);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream("C:\\Users\\lenovo\\eclipse-workspace\\baeldung\\java-object-creation\\src\\com\\baeldung\\option4\\person_details.txt");
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            Person p2 = (Person) inputStream.readObject();
            System.out.println("Name: " + p2.getName());
            System.out.println("UID: " + p2.getUid());
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
