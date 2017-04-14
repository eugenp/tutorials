package com.baeldung.di.setterinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class represents a Student instance.
 *
 */
@Component
public class Student {

    private PersonalDetails personalDetails;// personal information
    private Department department;// department to which student belongs to

    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    @Autowired
    public void setPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
    }

    public Department getDepartment() {
        return department;
    }

    @Autowired
    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        StringBuffer sBuf = new StringBuffer();
        sBuf.append("Student ==> ")
            .append(personalDetails)
            .append(department);
        return sBuf.toString();
    }

}
