package com.baeldung.java14.helpfulnullpointerexceptions;

public class HelpfulNullPointerException {

    public static void main(String[] args) {
        Employee employee = null;
        employee.getName();
    }

    public String getEmployeeEmailAddress(Employee employee) {
        String emailAddress = employee.getPersonalDetails().getEmailAddress().toLowerCase();
        return emailAddress;
    }

    static class Employee {
        String name;
        PersonalDetails personalDetails;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public PersonalDetails getPersonalDetails() {
            return personalDetails;
        }

        public void setPersonalDetails(PersonalDetails personalDetails) {
            this.personalDetails = personalDetails;
        }
    }

    static class PersonalDetails {
        String emailAddress;
        String phone;

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
