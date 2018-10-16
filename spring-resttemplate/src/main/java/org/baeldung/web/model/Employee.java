package org.baeldung.web.model;

import java.util.Date;
import java.util.Objects;

public class Employee {

        private String id;
        private String name;
        private Double salary;

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

        public Double getSalary() {
                return salary;
        }

        public void setSalary(Double salary) {
                this.salary = salary;
        }

        @Override public String toString() {
                return "Employee{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", salary=" + salary + '}';
        }

        @Override public boolean equals(Object o) {
                if (this == o)
                        return true;
                if (o == null || getClass() != o.getClass())
                        return false;
                Employee employee = (Employee) o;
                return Objects.equals(id, employee.id) && Objects.equals(name, employee.name) && Objects.equals(salary, employee.salary);
        }

        @Override public int hashCode() {

                return Objects.hash(id, name, salary);
        }
}
