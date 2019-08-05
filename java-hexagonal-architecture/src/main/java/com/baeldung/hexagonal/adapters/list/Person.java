package com.baeldung.hexagonal.adapters.list;

import java.util.Objects;

public class Person {

        String name;
        String phoneNumber;

        public Person(String name, String phoneNumber) {
                this.name = name;
                this.phoneNumber = phoneNumber;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getPhoneNumber() {
                return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
        }

        @Override public boolean equals(Object o) {
                if (this == o)
                        return true;
                if (o == null || getClass() != o.getClass())
                        return false;
                Person person = (Person) o;
                return Objects.equals(name, person.name) && Objects.equals(phoneNumber, person.phoneNumber);
        }

        @Override public int hashCode() {
                return Objects.hash(name, phoneNumber);
        }
}


