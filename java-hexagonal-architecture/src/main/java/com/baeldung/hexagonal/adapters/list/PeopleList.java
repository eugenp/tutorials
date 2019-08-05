package com.baeldung.hexagonal.adapters.list;

import java.util.List;

public class PeopleList {

        List<Person> people;

        public PeopleList(List<Person> people) {
                this.people = people;
        }

        public List<Person> getPeople() {
                return people;
        }

        public void setPeople(List<Person> people) {
                this.people = people;
        }
}
