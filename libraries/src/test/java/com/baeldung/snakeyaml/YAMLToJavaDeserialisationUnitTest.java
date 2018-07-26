package com.baeldung.snakeyaml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.Date;
import java.util.Map;

import org.junit.Test;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.baeldung.snakeyaml.Contact;
import com.baeldung.snakeyaml.Customer;

public class YAMLToJavaDeserialisationUnitTest {

    @Test
    public void whenLoadYAMLDocument_thenLoadCorrectMap() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
            .getClassLoader()
            .getResourceAsStream("yaml/customer.yaml");
        Map<String, Object> obj = yaml.load(inputStream);
        System.out.println(obj);
    }

    @Test
    public void whenLoadYAMLDocumentWithTopLevelClass_thenLoadCorrectJavaObject() {
        Yaml yaml = new Yaml(new Constructor(Customer.class));
        InputStream inputStream = this.getClass()
            .getClassLoader()
            .getResourceAsStream("yaml/customer.yaml");
        Customer customer = yaml.load(inputStream);
        System.out.println(customer);
    }

    @Test
    public void whenLoadYAMLDocumentWithAssumedClass_thenLoadCorrectJavaObject() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
            .getClassLoader()
            .getResourceAsStream("yaml/customer_with_type.yaml");
        Customer customer = yaml.load(inputStream);
        System.out.println(customer);
    }

    @Test
    public void whenLoadYAML_thenLoadCorrectImplicitTypes() {
        Yaml yaml = new Yaml();
        Map<Object, Object> document = yaml.load("3.0: 2018-07-22");
        assertNotNull(document);
        assertTrue(document.size() == 1);
        assertTrue(document.containsKey(Double.valueOf(3.0)));
        assertTrue(document.get(Double.valueOf(3.0)) instanceof Date);
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
        assertTrue(customer.getAge() == 31);
        assertNotNull(customer.getContactDetails());
        assertTrue(customer.getContactDetails()
            .size() == 2);
        assertEquals("mobile", customer.getContactDetails()
            .get(0)
            .getType());
        assertTrue(customer.getContactDetails()
            .get(0)
            .getNumber() == 123456789);
        assertEquals("landline", customer.getContactDetails()
            .get(1)
            .getType());
        assertTrue(customer.getContactDetails()
            .get(1)
            .getNumber() == 456786868);
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
        assertTrue(customer.getAge() == 31);
        assertNotNull(customer.getContactDetails());
        assertTrue(customer.getContactDetails()
            .size() == 2);
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
        assertTrue(count == 2);
    }

}
