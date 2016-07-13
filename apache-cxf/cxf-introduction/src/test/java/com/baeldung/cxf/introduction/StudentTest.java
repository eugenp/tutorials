package com.baeldung.cxf.introduction;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import com.baeldung.cxf.introduction.Baeldung;
import com.baeldung.cxf.introduction.Student;
import com.baeldung.cxf.introduction.StudentImpl;

public class StudentTest {
    private static QName SERVICE_NAME = new QName("http://introduction.cxf.baeldung.com/", "Baeldung");
    private static QName PORT_NAME = new QName("http://introduction.cxf.baeldung.com/", "BaeldungPort");

    private Service service;
    private Baeldung baeldungProxy;
    private BaeldungImpl baeldungImpl;

    {
        service = Service.create(SERVICE_NAME);
        String endpointAddress = "http://localhost:8080/baeldung";
        service.addPort(PORT_NAME, SOAPBinding.SOAP11HTTP_BINDING, endpointAddress);
    }

    @Before
    public void reinstantiateBaeldungInstances() {
        baeldungImpl = new BaeldungImpl();
        baeldungProxy = service.getPort(PORT_NAME, Baeldung.class);
    }

    @Test
    public void whenUsingHelloMethod_thenCorrect() {
        String endpointResponse = baeldungProxy.hello("Baeldung");
        String localResponse = baeldungImpl.hello("Baeldung");
        assertEquals(localResponse, endpointResponse);
    }

    @Test
    public void whenUsingHelloStudentMethod_thenCorrect() {
        Student student = new StudentImpl("John Doe");
        String endpointResponse = baeldungProxy.helloStudent(student);
        String localResponse = baeldungImpl.helloStudent(student);
        assertEquals(localResponse, endpointResponse);
    }

    @Test
    public void usingGetStudentsMethod_thenCorrect() {
        Student student1 = new StudentImpl("Adam");
        baeldungProxy.helloStudent(student1);

        Student student2 = new StudentImpl("Eve");
        baeldungProxy.helloStudent(student2);
        
        Map<Integer, Student> students = baeldungProxy.getStudents();       
        assertEquals("Adam", students.get(1).getName());
        assertEquals("Eve", students.get(2).getName());
    }
}