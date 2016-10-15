package com.baeldung.cxf.jaxrs.implementation;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("baeldung")
@Produces("text/xml")
public class Baeldung {
    private Map<Integer, Course> courses = new HashMap<>();

    {
        Student student1 = new Student();
        Student student2 = new Student();
        student1.setId(1);
        student1.setName("Student A");
        student2.setId(2);
        student2.setName("Student B");

        List<Student> course1Students = new ArrayList<>();
        course1Students.add(student1);
        course1Students.add(student2);

        Course course1 = new Course();
        Course course2 = new Course();
        course1.setId(1);
        course1.setName("REST with Spring");
        course1.setStudents(course1Students);
        course2.setId(2);
        course2.setName("Learn Spring Security");

        courses.put(1, course1);
        courses.put(2, course2);
    }

    @GET
    @Path("courses/{courseOrder}")
    public Course getCourse(@PathParam("courseOrder") int courseOrder) {
        return courses.get(courseOrder);
    }

    @PUT
    @Path("courses/{courseOrder}")
    public Response putCourse(@PathParam("courseOrder") int courseOrder, Course course) {
        Course existingCourse = courses.get(courseOrder);

        if (existingCourse == null || existingCourse.getId() != course.getId() || !(existingCourse.getName().equals(course.getName()))) {
            courses.put(courseOrder, course);
            return Response.ok().build();
        }

        return Response.notModified().build();
    }

    @Path("courses/{courseOrder}/students")
    public Course pathToStudent(@PathParam("courseOrder") int courseOrder) {
        return courses.get(courseOrder);
    }
}