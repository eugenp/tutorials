package com.baeldung.cxf.introduction;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.soap.SOAPBinding;

import org.junit.Before;
import org.junit.Test;

public class StudentLiveTest {
    private static QName SERVICE_NAME = new QName("http://introduction.cxf.baeldung.com/", "Baeldung");
    private static QName PORT_NAME = new QName("http://introduction.cxf.baeldung.com/", "BaeldungPort");

    private Service service;
    private Baeldung baeldungProxy;
    private BaeldungImpl baeldungImpl;

    {
        service = Service.create(SERVICE_NAME);
        final String endpointAddress = "http://localhost:8080/baeldung";
        service.addPort(PORT_NAME, SOAPBinding.SOAP11HTTP_BINDING, endpointAddress);
    }

    @Before
    public void reinstantiateBaeldungInstances() {
        baeldungImpl = new BaeldungImpl();
        baeldungProxy = service.getPort(PORT_NAME, Baeldung.class);
    }

    @Test
    public void whenUsingHelloMethod_thenCorrect() {
        final String endpointResponse = baeldungProxy.hello("Baeldung");
        final String localResponse = baeldungImpl.hello("Baeldung");
        assertEquals(localResponse, endpointResponse);
    }

    @Test
    public void whenUsingHelloStudentMethod_thenCorrect() {
        final Student student = new StudentImpl("John Doe");
        final String endpointResponse = baeldungProxy.helloStudent(student);
        final String localResponse = baeldungImpl.helloStudent(student);
        assertEquals(localResponse, endpointResponse);
    }

    @Test
    public void usingGetStudentsMethod_thenCorrect() {
        final Student student1 = new StudentImpl("Adam");
        baeldungProxy.helloStudent(student1);

        final Student student2 = new StudentImpl("Eve");
        baeldungProxy.helloStudent(student2);

        final Map<Integer, Student> students = baeldungProxy.getStudents();
        assertEquals("Adam", students.get(1).getName());
        assertEquals("Eve", students.get(2).getName());
    }
}