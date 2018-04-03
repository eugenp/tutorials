package com.baeldung.grails

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class StudentIntegrationSpec extends Specification {

    StudentService studentService
    SessionFactory sessionFactory

    private Long setupData() {
        new Student(firstName: 'John',lastName: 'Doe').save(flush: true, failOnError: true)
        new Student(firstName: 'Max',lastName: 'Foo').save(flush: true, failOnError: true)
        Student student = new Student(firstName: 'Alex',lastName: 'Bar').save(flush: true, failOnError: true)
        student.id
    }

    void "test get"() {
        Long id = setupData()

        expect:
        Student student = studentService.get(id)
        student.firstName == 'Alex'
        student.lastName == 'Bar'
    }

    void "test list"() {
        setupData()

        when:
        List<Student> studentList = studentService.list()

        then:
        studentList.size() == 3
        studentList[0].lastName == 'Doe'
        studentList[1].lastName == 'Foo'
        studentList[2].lastName == 'Bar'
    }


    void "test delete"() {
        Long id = setupData()

        expect:
        studentService.list().size() == 3

        when:
        studentService.delete(id)
        sessionFactory.currentSession.flush()

        then:
        studentService.list().size() == 2
    }

    void "test save"() {
        when:
        Student student = new Student(firstName: 'John',lastName: 'Doe')
        studentService.save(student)

        then:
        student.id != null
    }
}
