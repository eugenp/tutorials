package com.baeldung.libraries.snakeyaml;

import org.junit.Test;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.Date;
import java.util.Map;

import static org.junit.Assert.*;

public class YAMLToJavaDeserialisationUnitTest {

    @Test
    public void whenLoadYAMLDocument_thenLoadCorrectMap() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
            .getClassLoader()
            .getResourceAsStream("yaml/customer.yaml");
        Map<String, Object> obj = yaml.load(inputStream);
        assertEquals("John", obj.get("firstName"));
        assertEquals("Doe", obj.get("lastName"));
        assertEquals(20, obj.get("age"));
    }

    @Test
    public void whenLoadYAMLDocumentWithTopLevelClass_thenLoadCorrectJavaObject() {
        Yaml yaml = new Yaml(new Constructor(Customer.class));
        InputStream inputStream = this.getClass()
            .getClassLoader()
            .getResourceAsStream("yaml/customer.yaml");
        Customer customer = yaml.load(inputStream);
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals(20, customer.getAge());
    }

    @Test
    public void whenLoadYAMLDocumentWithAssumedClass_thenLoadCorrectJavaObject() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
            .getClassLoader()
            .getResourceAsStream("yaml/customer_with_type.yaml");
        Customer customer = yaml.load(inputStream);
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals(20, customer.getAge());
    }

    @Test
    public void whenLoadYAML_thenLoadCorrectImplicitTypes() {
        Yaml yaml = new Yaml();
        Map<Object, Object> document = yaml.load("3.0: 2018-07-22");
        assertNotNull(document);
        assertEquals(1, document.size());
        assertTrue(document.containsKey(3.0d));
        assertTrue(document.get(3.0d) instanceof Date);
    }

    @Test
    public void whenLoadYAMLDocumentWithTopLevelClass_thenLoadCorrectJavaObjectWithNestedObjects() {
        Yaml yaml = new Yaml(new Constructor(Customer.class));
        InputStream inputStream = this.getClass()
            .getClassLoader()
            .getResourceAsStream("yaml/customer_with_contact_details_and_address.yaml");
        Customer customer = yaml.load(inputStream);
        assertNotNull(customer);
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals(31, customer.getAge());
        assertNotNull(customer.getContactDetails());
        assertEquals(2, customer.getContactDetails().size());
        assertEquals("mobile", customer.getContactDetails()
            .get(0)
            .getType());
        assertEquals(123456789,customer.getContactDetails()
            .get(0)
            .getNumber());
        assertEquals("landline", customer.getContactDetails()
            .get(1)
            .getType());
        assertEquals(456786868, customer.getContactDetails()
            .get(1)
            .getNumber());
        assertNotNull(customer.getHomeAddress());
        assertEquals("Xyz, DEF Street", customer.getHomeAddress()
            .getLine());
    }

    @Test
    public void whenLoadYAMLDocumentWithTypeDescription_thenLoadCorrectJavaObjectWithCorrectGenericType() {
        Constructor constructor = new Constructor(Customer.class);
        TypeDescription customTypeDescription = new TypeDescription(Customer.class);
        customTypeDescription.addPropertyParameters("contactDetails", Contact.class);
        constructor.addTypeDescription(customTypeDescription);
        Yaml yaml = new Yaml(constructor);
        InputStream inputStream = this.getClass()
            .getClassLoader()
            .getResourceAsStream("yaml/customer_with_contact_details.yaml");
        Customer customer = yaml.load(inputStream);
        assertNotNull(customer);
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals(31, customer.getAge());
        assertNotNull(customer.getContactDetails());
        assertEquals(2, customer.getContactDetails().size());
        assertEquals("mobile", customer.getContactDetails()
            .get(0)
            .getType());
        assertEquals("landline", customer.getContactDetails()
            .get(1)
            .getType());
    }

    @Test
    public void whenLoadMultipleYAMLDocuments_thenLoadCorrectJavaObjects() {
        Yaml yaml = new Yaml(new Constructor(Customer.class));
        InputStream inputStream = this.getClass()
            .getClassLoader()
            .getResourceAsStream("yaml/customers.yaml");
        int count = 0;
        for (Object object : yaml.loadAll(inputStream)) {
            count++;
            assertTrue(object instanceof Customer);
        }
        assertEquals(2, count);
    }

}
