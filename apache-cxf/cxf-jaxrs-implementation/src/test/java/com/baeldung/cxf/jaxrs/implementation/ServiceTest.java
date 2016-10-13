package com.baeldung.cxf.jaxrs.implementation;

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

import javax.xml.bind.JAXB;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class ServiceTest {
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
    public void whenPutCourse_thenCorrect() throws IOException {
        HttpPut httpPut = new HttpPut(BASE_URL + "3");
        InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream("course.xml");
        httpPut.setEntity(new InputStreamEntity(resourceStream));
        httpPut.setHeader("Content-Type", "text/xml");
        
        HttpResponse response = client.execute(httpPut);
        assertEquals(200, response.getStatusLine().getStatusCode());
        
        Course course = getCourse(3);
        assertEquals(3, course.getId());
        assertEquals("Apache CXF Support for RESTful", course.getName());
    }

    @Test
    public void whenPostStudent_thenCorrect() throws IOException {
        HttpPost httpPost = new HttpPost(BASE_URL + "2/students");
        InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream("student.xml");
        httpPost.setEntity(new InputStreamEntity(resourceStream));
        httpPost.setHeader("Content-Type", "text/xml");
        
        HttpResponse response = client.execute(httpPost);
        assertEquals(200, response.getStatusLine().getStatusCode());
        
        Student student = getStudent(2, 0);
        assertEquals(3, student.getId());
        assertEquals("Student C", student.getName());
    }
    
    @Test
    public void whenDeleteStudent_thenCorrect() throws IOException {
        HttpDelete httpDelete = new HttpDelete(BASE_URL + "1/students/0");
        HttpResponse response = client.execute(httpDelete);
        assertEquals(200, response.getStatusLine().getStatusCode());
        
        Course course = getCourse(1);
        assertEquals(1, course.getStudents().size());
        assertEquals(2, course.getStudents().get(0).getId());
        assertEquals("Student B", course.getStudents().get(0).getName());
    }

    private Course getCourse(int courseOrder) throws IOException {
        URL url = new URL(BASE_URL + courseOrder);
        InputStream input = url.openStream();
        return JAXB.unmarshal(new InputStreamReader(input), Course.class);
    }

    private Student getStudent(int courseOrder, int studentOrder) throws IOException {
        URL url = new URL(BASE_URL + courseOrder + "/students/" + studentOrder);
        InputStream input = url.openStream();
        return JAXB.unmarshal(new InputStreamReader(input), Student.class);
    }
}