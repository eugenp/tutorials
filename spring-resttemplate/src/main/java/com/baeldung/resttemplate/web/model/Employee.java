package com.baeldung.resttemplate.web.model;

import java.util.Objects;

public class Employee {

        private String id;
        private String name;

        public Employee(String id, String name) {
                this.id = id;
                this.name = name;
        }

        public Employee() {
        }

        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        @Override public boolean equals(Object o) {
                if (this == o)
                        return true;
                if (o == null || getClass() != o.getClass())
                        return false;
                Employee employee = (Employee) o;
                return Objects.equals(id, employee.id);
        }

        @Override public int hashCode() {

                return Objects.hash(id);
        }
}
