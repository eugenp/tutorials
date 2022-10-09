package com.baeldung.enumeration_iterator_differences;

import java.util.Iterator;
import java.util.List;

import static com.baeldung.enumeration_iterator_differences.DataUtil.getPersons;

/**
 * @author amitkumar
 */
public class IteratorExample {
    public static void main(String[] args) {
        List<Person> persons = getPersons();
        Iterator<Person> iterator = persons.iterator();
        while (iterator.hasNext()) {
            System.out.println("First Name = " + iterator.next()
              .getFirstName());
        }
    }
}
