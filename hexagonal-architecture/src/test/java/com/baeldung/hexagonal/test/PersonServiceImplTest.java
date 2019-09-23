package com.baeldung.hexagonal.test;

import com.baeldung.hexagonal.Person;
import com.baeldung.hexagonal.PersonService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PersonServiceImplTest implements PersonService {

    @Override
    public void persist(Person person) {
        String file = "d:\\data.txt";
        byte[] bytes = person.toString().getBytes();
        try (OutputStream out = new FileOutputStream(file)) {
            out.write(bytes);
        } catch (IOException e) {
            // log error
        }
    }

}
