package com.baeldung.resource;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.util.ResourceUtils;

/**
 * Test class illustrating various methods of accessing a file from the classpath using Resource.
 * @author tritty
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class SpringResourceIntegrationTest {
    /**
     * Resource loader instance for lazily loading resources.
     */
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ApplicationContext appContext;

    /**
     * Injecting resource
     */
    @Value("classpath:data/employees.dat")
    private Resource resourceFile;

    /**
     * Data in data/employee.dat
     */
    private static final String EMPLOYEES_EXPECTED = "Joe Employee,Jan Employee,James T. Employee";

    @Test
    public void whenResourceLoader_thenReadSuccessful() throws IOException {
        final Resource resource = resourceLoader.getResource("classpath:data/employees.dat");
        final String employees = new String(Files.readAllBytes(resource.getFile()
            .toPath()));
        assertEquals(EMPLOYEES_EXPECTED, employees);
    }

    @Test
    public void whenApplicationContext_thenReadSuccessful() throws IOException {
        final Resource resource = appContext.getResource("classpath:data/employees.dat");
        final String employees = new String(Files.readAllBytes(resource.getFile()
            .toPath()));
        assertEquals(EMPLOYEES_EXPECTED, employees);
    }

    @Test
    public void whenAutowired_thenReadSuccessful() throws IOException {
        final String employees = new String(Files.readAllBytes(resourceFile.getFile()
            .toPath()));
        assertEquals(EMPLOYEES_EXPECTED, employees);
    }

    @Test
    public void whenResourceUtils_thenReadSuccessful() throws IOException {
        final File employeeFile = ResourceUtils.getFile("classpath:data/employees.dat");
        final String employees = new String(Files.readAllBytes(employeeFile.toPath()));
        assertEquals(EMPLOYEES_EXPECTED, employees);
    }

    @Test
    public void whenResourceAsStream_thenReadSuccessful() throws IOException {
        final InputStream resource = new ClassPathResource("data/employees.dat").getInputStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource))) {
            final String employees = reader.lines()
                .collect(Collectors.joining("\n"));
            assertEquals(EMPLOYEES_EXPECTED, employees);
        }
    }

    @Test
    public void whenResourceAsFile_thenReadSuccessful() throws IOException {
        final File resource = new ClassPathResource("data/employees.dat").getFile();
        final String employees = new String(Files.readAllBytes(resource.toPath()));
        assertEquals(EMPLOYEES_EXPECTED, employees);
    }
    
    @Test
    public void whenClassPathResourceWithAbsoultePath_thenReadSuccessful() throws IOException {
    	final File resource = new ClassPathResource("/data/employees.dat", this.getClass()).getFile();
        final String employees = new String(Files.readAllBytes(resource.toPath()));
        assertEquals(EMPLOYEES_EXPECTED, employees);
    }
    
    @Test
    public void whenClassPathResourceWithRelativePath_thenReadSuccessful() throws IOException {
    	final File resource = new ClassPathResource("../../../data/employees.dat", SpringResourceIntegrationTest.class).getFile();
        final String employees = new String(Files.readAllBytes(resource.toPath()));
        assertEquals(EMPLOYEES_EXPECTED, employees);
    }
}
