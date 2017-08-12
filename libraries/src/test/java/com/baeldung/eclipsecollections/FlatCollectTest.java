package com.baeldung.eclipsecollections;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.list.mutable.FastList;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class FlatCollectTest {

    MutableList<String> addresses1;
    MutableList<String> addresses2;
    MutableList<String> addresses3;
    MutableList<String> addresses4;

    MutableList<Student> students;

    @Before
    public void setup() {
        String address1 = "73 Pacific St., Forest Hills, NY 11375";
        String address2 = "93 Bayport Ave., South Richmond Hill, NY 11419";
        String address3 = "548 Market St, San Francisco, CA 94104";
        String address4 = "8605 Santa Monica Blvd, West Hollywood, CA 90069";

        this.addresses1 = FastList.newListWith(address1, address2);
        this.addresses2 = FastList.newListWith(address3, address4);
        Student student1 = new Student("John", "Hopkins", addresses1);
        Student student2 = new Student("George", "Adams", addresses2);
        this.addresses2 = FastList.newListWith(address3, address4);
        this.students = FastList.newListWith(student1, student2);
    }

    @Test
    public void whenFlatCollect_thenCorrect() {
        MutableList<String> addresses = students.flatCollect(Student::getAddresses);

        assertEquals("73 Pacific St., Forest Hills, NY 11375", addresses.get(0));
        assertEquals("93 Bayport Ave., South Richmond Hill, NY 11419", addresses.get(1));
        assertEquals("548 Market St, San Francisco, CA 94104", addresses.get(2));
        assertEquals("8605 Santa Monica Blvd, West Hollywood, CA 90069", addresses.get(3));
    }
}
