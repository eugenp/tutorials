package com.baeldung.beaninjection;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.Spring5Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Spring5Application.class)
public class InjectionApplicationTest {

    @Autowired
    private Employee employee;

    @Autowired
    private Account account;

    @Autowired
    private Address address;

    @Test
    public void whenConstructorInjected_thenEmployeeAddressAvailable() {
        String streetName = "Dodge Street";
        address.setStreet(streetName);

        assertEquals("Address does not match", employee.getAddress()
            .getStreet(), streetName);
    }

    @Test
    public void whenSetterInjected_thenEmployeeAccountAvailable() {
        Long accountNumber = 1234L;
        account.setAccountNumber(accountNumber);

        assertEquals("Account Number does not match", employee.getAccount()
            .getAccountNumber(), accountNumber);
    }

    @Test
    public void whenConstructorInjected_thenStudentAddressAvailable() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Address address = (Address) context.getBean("address");
        String streetName = "Main Road";
        address.setStreet(streetName);
        Student student = (Student) context.getBean("student");

        assertEquals("Street Name does not match", student.getAddress()
            .getStreet(), streetName);
    }

    @Test
    public void whenSetterInjected_thenSchoolCourseAvailable() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Course course = (Course) context.getBean("course");
        String courseName = "Mathematics";
        course.setName(courseName);
        School school = (School) context.getBean("school");

        assertEquals("Course Name does not match", school.getCourse()
            .getName(), courseName);
    }

}
