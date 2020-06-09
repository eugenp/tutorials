package com.baeldung.pattern.javahexagonalarch.domain;

public class Employee {

        private int employeeId;
        private String firstName;
        private String lastName;

        public Employee(int employeeId, String firstName, String lastName) {
                super();
                this.employeeId = employeeId;
                this.firstName = firstName;
                this.lastName = lastName;
        }

        @Override public String toString() {
                return "Employee [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName + "]";
        }

        public int getEmployeeId() {
                return employeeId;
        }

        public void setEmployeeId(int employeeId) {
                this.employeeId = employeeId;
        }

        public String getFirstName() {
                return firstName;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

}
