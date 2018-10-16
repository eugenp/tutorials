package org.baeldung.web.dto;

import java.util.Date;

public class EmployeeDto {

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
                return "EmployeeDto{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", salary=" + salary + '}';
        }
}
