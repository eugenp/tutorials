package com.baeldung.springgroovyconfig;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;
import org.springframework.util.ResourceUtils;

import groovy.lang.Binding;
import groovy.lang.GroovyObject;
import groovy.util.GroovyScriptEngine;

class SpringGroovyConfigUnitTest {

    private static final String FILE_NAME = "GroovyBeanBuilder.groovy";
    private static final String FILE_PATH = "src/main/groovy/com/baeldung/springgroovyconfig/";

    @Test
    void givenGroovyConfigFile_whenCalledWithBeanName_thenReturnCompanyBean() {
        try (GenericGroovyApplicationContext ctx = new GenericGroovyApplicationContext()) {
            ctx.load("file:" + getPath(FILE_PATH) + FILE_NAME);
            ctx.refresh();

            Company company = (Company) ctx.getBean("companyBean");

            assertEquals("ABC Inc", company.getName());
        } catch (BeansException | IllegalStateException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void givenGroovyConfigFile_whenCalledWithRefBean_thenReturnEmployeeBean() {
        try (GenericGroovyApplicationContext ctx = new GenericGroovyApplicationContext()) {
            ctx.load("file:" + getPath(FILE_PATH) + FILE_NAME);
            ctx.refresh();

            Employee employee = ctx.getBean(Employee.class);

            assertEquals("Lakshmi", employee.getFirstName());
            assertEquals("Priya", employee.getLastName());
            assertEquals("XYZ Inc", employee.getCompany()
                .getName());
        } catch (BeansException | IllegalStateException e) {
            fail(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    void givenGroovyFileWithSpringAnnotations_whenCalledWithBeanName_thenReturnValidBean() {

        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();) {
            ctx.register(SpringGroovyConfiguration.class);
            ctx.refresh();

            List<String> fruits = (List<String>) ctx.getBean("fruits");

            assertNotNull(fruits);
            assertTrue(fruits.size() == 4);
            assertEquals("Apple", fruits.get(0));
        } catch (BeansException | IllegalStateException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void givenGroovyBeanConfiguredInXml_whenCalledWithBeanName_thenReturnValidBean() {
        try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("groovy-xml-config.xml")) {
            ctx.refresh();

            NotificationService notifier = (NotificationService) ctx.getBean("notification");

            assertEquals("Hello", notifier.getMessage());
        } catch (BeansException | IllegalStateException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void givenGroovyBeanConfiguredAsInlineScript_whenCalledWithBeanName_thenReturnValidBean() {
        try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("groovy-xml-config.xml")) {
            ctx.refresh();

            NotificationService notifier = (NotificationService) ctx.getBean("notifier");

            assertEquals("Have a nice day!", notifier.getMessage());
        } catch (BeansException | IllegalStateException e) {
            fail(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    void givenGroovyScript_whenCalledWithScriptEngine_thenReturnsResult() {
        try {
            GroovyScriptEngine engine = new GroovyScriptEngine(ResourceUtils.getFile("file:src/main/resources/")
                .getAbsolutePath(), this.getClass().getClassLoader());
            Class<GroovyObject> joinerClass = engine.loadScriptByName("StringJoiner.groovy");
            GroovyObject joiner = joinerClass.getDeclaredConstructor()
                .newInstance();
            Object result = joiner.invokeMethod("join", new Object[] { "Mr.", "Bob" });
            
            assertEquals("Mr.Bob", result.toString());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    void givenGroovyScript_whenCalledWithBindingObject_thenReturnsResult() {
        try {
            GroovyScriptEngine engine = new GroovyScriptEngine(ResourceUtils.getFile("file:src/main/resources/")
                .getAbsolutePath(), this.getClass().getClassLoader());
            Binding binding = new Binding();
            binding.setVariable("arg1", "Mr.");
            binding.setVariable("arg2", "Bob");
            Object result = engine.run("StringJoinerScript.groovy", binding);
            
            assertEquals("Mr.Bob", result.toString());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    private String getPath(String filePath) {
        String pathPart = new File(".").getAbsolutePath();
        pathPart = pathPart.replace(".", "");
        pathPart = pathPart.replace("\\", "/");
        pathPart = pathPart + filePath;

        return pathPart;
    }

}
