package com.baeldung.eclipsecollections;

import org.assertj.core.api.Assertions;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.list.mutable.FastList;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FlatCollectUnitTest {

    MutableList<String> addresses1;
    MutableList<String> addresses2;
    MutableList<String> addresses3;
    MutableList<String> addresses4;

    List<String> expectedAddresses;
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
        this.expectedAddresses = new ArrayList<>();
        this.expectedAddresses.add("73 Pacific St., Forest Hills, NY 11375");
        this.expectedAddresses.add("93 Bayport Ave., South Richmond Hill, NY 11419");
        this.expectedAddresses.add("548 Market St, San Francisco, CA 94104");
        this.expectedAddresses.add("8605 Santa Monica Blvd, West Hollywood, CA 90069");
    }

    @Test
    public void whenFlatCollect_thenCorrect() {
        MutableList<String> addresses = students.flatCollect(Student::getAddresses);

        Assertions.assertThat(addresses).containsExactlyElementsOf(this.expectedAddresses);
    }
}
