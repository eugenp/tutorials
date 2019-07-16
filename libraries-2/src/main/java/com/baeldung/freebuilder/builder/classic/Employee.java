package com.baeldung.freebuilder.builder.classic;

public class Employee {

    private String name;
    private int age;
    private String department;
    private String role;
    private String supervisorName;
    private String designation;
    private String email;
    private long phoneNumber;
    private boolean isPermanent;
    private Address address;

    private Employee() {

    }

    private void setName(String name) {
        this.name = name;
    }

    private void setAge(int age) {
        this.age = age;
    }

    private void setDepartment(String department) {
        this.department = department;
    }

    private void setRole(String role) {
        this.role = role;
    }

    private void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    private void setDesignation(String designation) {
        this.designation = designation;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private void setPermanent(boolean permanent) {
        isPermanent = permanent;
    }

    private void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDepartment() {
        return department;
    }

    public String getRole() {
        return role;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public String getDesignation() {
        return designation;
    }

    public String getEmail() {
        return email;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isPermanent() {
        return isPermanent;
    }

    public Address getAddress() {
        return address;
    }

    public static class Builder {

        private String name;
        private int age;
        private String department;
        private String role;
        private String supervisorName;
        private String designation;
        private String email;
        private long phoneNumber;
        private boolean isPermanent;
        private Address address;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setDepartment(String department) {
            this.department = department;
            return this;
        }

        public Builder setRole(String role) {
            this.role = role;
            return this;
        }

        public Builder setSupervisorName(String supervisorName) {
            this.supervisorName = supervisorName;
            return this;
        }

        public Builder setDesignation(String designation) {
            this.designation = designation;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPhoneNumber(long phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setPermanent(boolean permanent) {
            isPermanent = permanent;
            return this;
        }

        public Builder setAddress(Address address) {
            this.address = address;
            return this;
        }

        public Employee build() {
            Employee employee = new Employee();
            employee.setName(this.name);
            employee.setAge(this.age);
            employee.setDepartment(this.department);
            employee.setAddress(this.address);
            employee.setDesignation(this.designation);
            employee.setEmail(this.email);
            employee.setPermanent(this.isPermanent);
            employee.setName(this.name);
            employee.setSupervisorName(this.supervisorName);
            employee.setPhoneNumber(this.phoneNumber);

            return employee;

        }
    }

}
