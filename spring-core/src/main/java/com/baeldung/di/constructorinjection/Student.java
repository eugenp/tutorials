package com.baeldung.di.constructorinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.di.constructorinjection.Department;
import com.baeldung.di.constructorinjection.PersonalDetails;

/**
 * This class represents a Student instance.
 *
 */
@Component
public class Student {

    private PersonalDetails personalDetails;// personal information
    private Department department;// department to which student belongs to

    @Autowired
    public Student(PersonalDetails personalDetails, Department department) {
        this.personalDetails = personalDetails;
        this.department = department;
    }

    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
    }

    public Department getDepartment() {
        return department;
    }

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
