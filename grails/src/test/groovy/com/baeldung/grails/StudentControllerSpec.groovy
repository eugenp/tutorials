package com.baeldung.grails

import grails.testing.gorm.DomainUnitTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.*

class StudentControllerSpec extends Specification implements ControllerUnitTest<StudentController>, DomainUnitTest<Student> {

    void "Test the index action returns the correct model"() {
        given:
        controller.studentService = Mock(StudentService) {
            list() >> [new Student(firstName: 'John',lastName: 'Doe')]
        }

        when:"The index action is executed"
        controller.index()

        then:"The model is correct"
        model.studentList.size() == 1
        model.studentList[0].firstName == 'John'
        model.studentList[0].lastName == 'Doe'
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
        controller.create()

        then:"The model is correctly created"
        model.student!= null
    }


    void "Test the show action with a null id"() {
        given:
        controller.studentService = Mock(StudentService) {
            1 * get(null) >> null
        }

        when:"The show action is executed with a null domain"
        controller.show(null)

        then:"A 404 error is returned"
        response.status == 404
    }

    void "Test the show action with a valid id"() {
        given:
        controller.studentService = Mock(StudentService) {
            1 * get(2) >> new Student(firstName: 'John',lastName: 'Doe')
        }

        when:"A domain instance is passed to the show action"
        controller.show(2)

        then:"A model is populated containing the domain instance"
        model.student instanceof Student
        and:"And student is John Doe"
        model.student.firstName == 'John'
        model.student.lastName == 'Doe'
    }

    void "Test the delete action with an instance"() {
        given:
        controller.studentService = Mock(StudentService) {
            1 * delete(2)
        }

        when:"The domain instance is passed to the delete action"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'DELETE'
        controller.delete(2)

        then:"The user is redirected to index"
        response.redirectedUrl == '/student/index'
    }
}






