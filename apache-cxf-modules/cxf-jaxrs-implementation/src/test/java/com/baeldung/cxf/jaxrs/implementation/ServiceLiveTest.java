package com.baeldung.cxf.jaxrs.implementation;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import jakarta.xml.bind.JAXB;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ServiceLiveTest {
    private static final String BASE_URL = "http://localhost:8080/baeldung/courses/";
    private static CloseableHttpClient client;

    @BeforeClass
    public static void createClient() {
        client = HttpClients.createDefault();
    }

    @AfterClass
    public static void closeClient() throws IOException {
        client.close();
    }

    @Test
    public void whenUpdateNonExistentCourse_thenReceiveNotFoundResponse() throws IOException {
        final HttpPut httpPut = new HttpPut(BASE_URL + "3");
        final InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream("non_existent_course.xml");
        httpPut.setEntity(new InputStreamEntity(resourceStream));
        httpPut.setHeader("Content-Type", "text/xml");

        final HttpResponse response = client.execute(httpPut);
        assertEquals(404, response.getStatusLine().getStatusCode());
    }

    @Test
    public void whenUpdateUnchangedCourse_thenReceiveNotModifiedResponse() throws IOException {
        final HttpPut httpPut = new HttpPut(BASE_URL + "1");
        final InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream("unchanged_course.xml");
        httpPut.setEntity(new InputStreamEntity(resourceStream));
        httpPut.setHeader("Content-Type", "text/xml");

        final HttpResponse response = client.execute(httpPut);
        assertEquals(304, response.getStatusLine().getStatusCode());
    }

    @Test
    public void whenUpdateValidCourse_thenReceiveOKResponse() throws IOException {
        final HttpPut httpPut = new HttpPut(BASE_URL + "2");
        final InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream("changed_course.xml");
        httpPut.setEntity(new InputStreamEntity(resourceStream));
        httpPut.setHeader("Content-Type", "text/xml");

        final HttpResponse response = client.execute(httpPut);
        assertEquals(200, response.getStatusLine().getStatusCode());

        final Course course = getCourse(2);
        assertEquals(2, course.getId());
        assertEquals("Apache CXF Support for RESTful", course.getName());
    }

    @Test
    public void whenCreateConflictStudent_thenReceiveConflictResponse() throws IOException {
        final HttpPost httpPost = new HttpPost(BASE_URL + "1/students");
        final InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream("conflict_student.xml");
        httpPost.setEntity(new InputStreamEntity(resourceStream));
        httpPost.setHeader("Content-Type", "text/xml");

        final HttpResponse response = client.execute(httpPost);
        assertEquals(409, response.getStatusLine().getStatusCode());
    }

    @Test
    public void whenCreateValidStudent_thenReceiveOKResponse() throws IOException {
        final HttpPost httpPost = new HttpPost(BASE_URL + "2/students");
        final InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream("created_student.xml");
        httpPost.setEntity(new InputStreamEntity(resourceStream));
        httpPost.setHeader("Content-Type", "text/xml");

        final HttpResponse response = client.execute(httpPost);
        assertEquals(200, response.getStatusLine().getStatusCode());

        final Student student = getStudent(2, 3);
        assertEquals(3, student.getId());
        assertEquals("Student C", student.getName());
    }

    @Test
    public void whenDeleteInvalidStudent_thenReceiveNotFoundResponse() throws IOException {
        final HttpDelete httpDelete = new HttpDelete(BASE_URL + "1/students/3");
        final HttpResponse response = client.execute(httpDelete);
        assertEquals(404, response.getStatusLine().getStatusCode());
    }

    @Test
    public void whenDeleteValidStudent_thenReceiveOKResponse() throws IOException {
        final HttpDelete httpDelete = new HttpDelete(BASE_URL + "1/students/1");
        final HttpResponse response = client.execute(httpDelete);
        assertEquals(200, response.getStatusLine().getStatusCode());

        final Course course = getCourse(1);
        assertEquals(1, course.getStudents().size());
        assertEquals(2, course.getStudents().get(0).getId());
        assertEquals("Student B", course.getStudents().get(0).getName());
    }

    private Course getCourse(int courseOrder) throws IOException {
        final URL url = new URL(BASE_URL + courseOrder);
        final InputStream input = url.openStream();
        return JAXB.unmarshal(new InputStreamReader(input), Course.class);
    }

    private Student getStudent(int courseOrder, int studentOrder) throws IOException {
        final URL url = new URL(BASE_URL + courseOrder + "/students/" + studentOrder);
        final InputStream input = url.openStream();
        return JAXB.unmarshal(new InputStreamReader(input), Student.class);
    }
}