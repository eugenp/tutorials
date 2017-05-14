package com.baeldung;

import com.baeldung.config.AppConfig;
import com.baeldung.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

public class SpringDependenciesInjectionTestApp {

    private static Logger logger = LoggerFactory.getLogger(SpringDependenciesInjectionTestApp.class);

    public static void main(String[] args) {
        Employee empFromJava = getEmployeeFromJavaConfig();
        logger.info("Employee from Java Config "+empFromJava.toString());
        assertEmployeeFromJavaConfig(empFromJava);

        Employee empFromXML = getEmployeeFromXml();
        assertEmployeeFromXMLConfig(empFromXML);
        logger.info("Employee from XML Config "+empFromXML.toString());
    }

    private static Employee getEmployeeFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        return context.getBean(Employee.class);
    }

    private static Employee getEmployeeFromXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        Object obj =  context.getBean("defaultEmployee");
        if(obj instanceof  Employee) {
            return (Employee) obj;
        }
        return null;
    }

    private static void assertEmployeeFromJavaConfig(Employee employee){
        Assert.notNull(employee);
        Assert.isTrue("Emily".equals(employee.getFirstName()));
        Assert.isTrue("Johnson".equals(employee.getLastName()));
        Assert.isTrue("SALES".equals(employee.getDepartment().name()));
        Assert.notNull(employee.getAddress());
        Assert.isTrue("USA".equals(employee.getAddress().getCountry()));
        Assert.isTrue("CA".equals(employee.getAddress().getState()));
        Assert.isTrue("ABC".equals(employee.getAddress().getCity()));
        Assert.isTrue("Street 1".equals(employee.getAddress().getStreet()));
        Assert.isTrue("94522".equals(employee.getAddress().getZipcode()));
        logger.info("Test: Employee dependency injection using Java config is successful.");
    }

    private static void assertEmployeeFromXMLConfig(Employee employee){
        Assert.notNull(employee);
        Assert.isTrue("Kelly".equals(employee.getFirstName()));
        Assert.isTrue("Tineo".equals(employee.getLastName()));
        Assert.isTrue("HUMAN_RESOURCE".equals(employee.getDepartment().name()));

        Assert.notNull(employee.getAddress());
        Assert.isTrue("USA".equals(employee.getAddress().getCountry()));
        Assert.isTrue("NY".equals(employee.getAddress().getState()));
        Assert.isTrue("XYZ".equals(employee.getAddress().getCity()));
        Assert.isTrue("Street 2".equals(employee.getAddress().getStreet()));
        Assert.isTrue("00999".equals(employee.getAddress().getZipcode()));
        logger.info("Test: Employee dependency injection using XML config is successful.");
    }
}
