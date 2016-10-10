package com.baeldung.cxf.jaxrs.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Course")
public class Course {
    private int id;
    private String name;
    private List<Student> students;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
    
    @GET
    @Path("{studentOrder}")
    public Student getStudent(@PathParam("studentOrder")int studentOrder) {
        return students.get(studentOrder);
    }
    
    @POST
    public Response postStudent(Student student) {
        if (students == null) {
            students = new ArrayList<Student>();
        }
        students.add(student);
        return Response.ok(student).build();
    }
    
    @DELETE
    @Path("{studentOrder}")
    public Response deleteStudent(@PathParam("studentOrder") int studentOrder) {
        Student student = students.get(studentOrder);
        Response response;
        if (student != null) {
            students.remove(studentOrder);
            response = Response.ok().build();
        } else {
            response = Response.notModified().build();
        }
        return response;
    }
}