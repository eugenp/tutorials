package com.baeldung.hexagonal.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.baeldung.hexagonal.beans.Student;
import com.baeldung.hexagonal.interfaces.StudentGuiPort;

@Path("gui-service")
public class StudentGuiAdapter implements StudentGuiPort {

    @Path("addstudent")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Override
    public Object addStudent(@QueryParam("firstname") String pFirstName, @QueryParam("lastname") String pLastName, @QueryParam("address") String pAddress) {

        Student student = new Student();
        student.setFirstName(pFirstName);
        student.setLastName(pLastName);
        student.setAddress(pAddress);

        Student savedStudent = new StudentDBAdapter().createStudent(student);

        Response response = Response.status(Status.OK)
            .entity(savedStudent)
            .build();

        return response;
    }

    @Path("student")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Override
    public Object view(@QueryParam("id") long id) {

        Student student = new StudentDBAdapter().findById(id);

        Response response = Response.status(Status.OK)
            .entity(student)
            .build();

        return response;
    }
}
