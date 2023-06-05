package com.baeldung.cxf.aegis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.cxf.aegis.AegisContext;
import org.apache.cxf.aegis.AegisReader;
import org.apache.cxf.aegis.AegisWriter;
import org.apache.cxf.aegis.type.AegisType;

public class BaeldungIntegrationTest {
    private AegisContext context;
    private String fileName = "baeldung.xml";

    @Test
    public void whenMarshalingAndUnmarshalingCourseRepo_thenCorrect() throws Exception {
        initializeContext();
        CourseRepo inputRepo = initCourseRepo();
        marshalCourseRepo(inputRepo);
        CourseRepo outputRepo = unmarshalCourseRepo();
        Course restCourse = outputRepo.getCourses().get(1);
        Course securityCourse = outputRepo.getCourses().get(2);
        assertEquals("Welcome to Beldung!", outputRepo.getGreeting());
        assertEquals("REST with Spring", restCourse.getName());
        assertEquals(new Date(1234567890000L), restCourse.getEnrolmentDate());
        assertNull(restCourse.getInstructor());
        assertEquals("Learn Spring Security", securityCourse.getName());
        assertEquals(new Date(1456789000000L), securityCourse.getEnrolmentDate());
        assertNull(securityCourse.getInstructor());
    }
    
    private void initializeContext() {
        context = new AegisContext();
        Set<Type> rootClasses = new HashSet<Type>();
        rootClasses.add(CourseRepo.class);
        context.setRootClasses(rootClasses);
        Map<Class<?>, String> beanImplementationMap = new HashMap<>();
        beanImplementationMap.put(CourseRepoImpl.class, "CourseRepo");
        context.setBeanImplementationMap(beanImplementationMap);
        context.setWriteXsiTypes(true);
        context.initialize();
    }
    
    private CourseRepoImpl initCourseRepo() {
        Course restCourse = new Course();
        restCourse.setId(1);
        restCourse.setName("REST with Spring");
        restCourse.setInstructor("Eugen");
        restCourse.setEnrolmentDate(new Date(1234567890000L));
        Course securityCourse = new Course();
        securityCourse.setId(2);
        securityCourse.setName("Learn Spring Security");
        securityCourse.setInstructor("Eugen");
        securityCourse.setEnrolmentDate(new Date(1456789000000L));
        CourseRepoImpl courseRepo = new CourseRepoImpl();
        courseRepo.setGreeting("Welcome to Beldung!");
        courseRepo.addCourse(restCourse);
        courseRepo.addCourse(securityCourse);
        return courseRepo;
    }
    
    private void marshalCourseRepo(CourseRepo courseRepo) throws Exception {
        AegisWriter<XMLStreamWriter> writer = context.createXMLStreamWriter();
        AegisType aegisType = context.getTypeMapping().getType(CourseRepo.class);
        final FileOutputStream stream = new FileOutputStream(fileName);
        XMLStreamWriter xmlWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(stream);
        writer.write(courseRepo, new QName("http://aegis.cxf.baeldung.com", "baeldung"), false, xmlWriter, aegisType);
        xmlWriter.close();
        stream.close();
    }

    private CourseRepo unmarshalCourseRepo() throws Exception {       
        AegisReader<XMLStreamReader> reader = context.createXMLStreamReader();
        final FileInputStream stream = new FileInputStream(fileName);
        XMLStreamReader xmlReader = XMLInputFactory.newInstance().createXMLStreamReader(stream);
        CourseRepo courseRepo = (CourseRepo) reader.read(xmlReader, context.getTypeMapping().getType(CourseRepo.class));
        xmlReader.close();
        stream.close();
        return courseRepo;
    }

    @After
    public void cleanup(){
        File testFile = new File(fileName);
        if (testFile.exists()) {
           testFile.deleteOnExit();
        }
    }
}