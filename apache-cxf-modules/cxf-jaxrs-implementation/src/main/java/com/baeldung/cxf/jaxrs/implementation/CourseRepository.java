package com.baeldung.cxf.jaxrs.implementation;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("baeldung")
@Produces("text/xml")
public class CourseRepository {
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
    @Path("courses/{courseId}")
    public Course getCourse(@PathParam("courseId") int courseId) {
        return findById(courseId);
    }

    @PUT
    @Path("courses/{courseId}")
    public Response updateCourse(@PathParam("courseId") int courseId, Course course) {
        Course existingCourse = findById(courseId);
        if (existingCourse == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (existingCourse.equals(course)) {
            return Response.notModified().build();
        }
        courses.put(courseId, course);
        return Response.ok().build();
    }

    @Path("courses/{courseId}/students")
    public Course pathToStudent(@PathParam("courseId") int courseId) {
        return findById(courseId);
    }

    private Course findById(int id) {
        for (Map.Entry<Integer, Course> course : courses.entrySet()) {
            if (course.getKey() == id) {
                return course.getValue();
            }
        }
        return null;
    }
}
