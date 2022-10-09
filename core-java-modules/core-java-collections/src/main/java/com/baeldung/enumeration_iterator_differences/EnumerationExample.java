package com.baeldung.enumeration_iterator_differences;

import java.util.Enumeration;
import java.util.Vector;

import static com.baeldung.enumeration_iterator_differences.DataUtil.getPersons;

/**
 * @author amitkumar
 */
public class EnumerationExample {
    public static void main(String[] args) {

        Vector<Person> people = new Vector<>(getPersons());
        Enumeration<Person> enumeration = people.elements();
        while (enumeration.hasMoreElements()) {
            System.out.println("people. = " + enumeration.nextElement()
              .getFirstName());
        }
    }

}
