package comparators;

import java.util.Comparator;

import model.Person;

public class PersonIdComparator implements Comparator<Person> {
    public int compare(Person one, Person two) {
        return one.getId()
            .compareTo(two.getId());
    }
}