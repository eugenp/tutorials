package com.baeldung.spring.data.couchbase.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.validation.ConstraintViolationException;

import com.baeldung.spring.data.couchbase.IntegrationTest;
import com.baeldung.spring.data.couchbase.MyCouchbaseConfig;
import com.baeldung.spring.data.couchbase.model.Student;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;

public abstract class StudentServiceLiveTest extends IntegrationTest {

    static final String typeField = "_class";
    static final String joe = "Joe";
    static final String college = "College";
    static final String joeCollegeId = "student:" + joe + ":" + college;
    static final DateTime joeCollegeDob = DateTime.now().minusYears(21);
    static final Student joeCollege = new Student(joeCollegeId, joe, college, joeCollegeDob);
    static final JsonObject jsonJoeCollege = JsonObject.empty().put(typeField, Student.class.getName()).put("firstName", joe).put("lastName", college).put("created", DateTime.now().getMillis()).put("version", 1);

    static final String judy = "Judy";
    static final String jetson = "Jetson";
    static final String judyJetsonId = "student:" + judy + ":" + jetson;
    static final DateTime judyJetsonDob = DateTime.now().minusYears(19).minusMonths(5).minusDays(3);
    static final Student judyJetson = new Student(judyJetsonId, judy, jetson, judyJetsonDob);
    static final JsonObject jsonJudyJetson = JsonObject.empty().put(typeField, Student.class.getName()).put("firstName", judy).put("lastName", jetson).put("created", DateTime.now().getMillis()).put("version", 1);

    StudentService studentService;

    @BeforeClass
    public static void setupBeforeClass() {
        Cluster cluster = CouchbaseCluster.create(MyCouchbaseConfig.NODE_LIST);
        Bucket bucket = cluster.openBucket(MyCouchbaseConfig.BUCKET_NAME, MyCouchbaseConfig.BUCKET_PASSWORD);
        bucket.upsert(JsonDocument.create(joeCollegeId, jsonJoeCollege));
        bucket.upsert(JsonDocument.create(judyJetsonId, jsonJudyJetson));
        bucket.close();
        cluster.disconnect();
    }

    @Test
    public void whenCreatingStudent_thenDocumentIsPersisted() {
        String firstName = "Eric";
        String lastName = "Stratton";
        DateTime dateOfBirth = DateTime.now().minusYears(25);
        String id = "student:" + firstName + ":" + lastName;
        Student expectedStudent = new Student(id, firstName, lastName, dateOfBirth);
        studentService.create(expectedStudent);
        Student actualStudent = studentService.findOne(id);
        assertNotNull(actualStudent.getCreated());
        assertNotNull(actualStudent);
        assertEquals(expectedStudent.getId(), actualStudent.getId());
    }

    @Test(expected = ConstraintViolationException.class)
    public void whenCreatingStudentWithInvalidFirstName_thenConstraintViolationException() {
        String firstName = "Er+ic";
        String lastName = "Stratton";
        DateTime dateOfBirth = DateTime.now().minusYears(25);
        String id = "student:" + firstName + ":" + lastName;
        Student student = new Student(id, firstName, lastName, dateOfBirth);
        studentService.create(student);
    }

    @Test(expected = ConstraintViolationException.class)
    public void whenCreatingStudentWithFutureDob_thenConstraintViolationException() {
        String firstName = "Jane";
        String lastName = "Doe";
        DateTime dateOfBirth = DateTime.now().plusDays(1);
        String id = "student:" + firstName + ":" + lastName;
        Student student = new Student(id, firstName, lastName, dateOfBirth);
        studentService.create(student);
    }

    @Test
    public void whenFindingStudentByJohnSmithId_thenReturnsJohnSmith() {
        Student actualStudent = studentService.findOne(joeCollegeId);
        assertNotNull(actualStudent);
        assertNotNull(actualStudent.getCreated());
        assertEquals(joeCollegeId, actualStudent.getId());
    }

    @Test
    public void whenFindingAllStudents_thenReturnsTwoOrMoreStudentsIncludingJoeCollegeAndJudyJetson() {
        List<Student> resultList = studentService.findAll();
        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
        assertTrue(resultContains(resultList, joeCollege));
        assertTrue(resultContains(resultList, judyJetson));
        assertTrue(resultList.size() >= 2);
    }

    @Test
    public void whenFindingByFirstNameJohn_thenReturnsOnlyStudentsNamedJohn() {
        String expectedFirstName = joe;
        List<Student> resultList = studentService.findByFirstName(expectedFirstName);
        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
        assertTrue(allResultsContainExpectedFirstName(resultList, expectedFirstName));
    }

    @Test
    public void whenFindingByLastNameSmith_thenReturnsOnlyStudentsNamedSmith() {
        String expectedLastName = college;
        List<Student> resultList = studentService.findByLastName(expectedLastName);
        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
        assertTrue(allResultsContainExpectedLastName(resultList, expectedLastName));
    }

    private boolean resultContains(List<Student> resultList, Student student) {
        boolean found = false;
        for (Student p : resultList) {
            if (p.getId().equals(student.getId())) {
                found = true;
                break;
            }
        }
        return found;
    }

    private boolean allResultsContainExpectedFirstName(List<Student> resultList, String firstName) {
        boolean found = false;
        for (Student p : resultList) {
            if (p.getFirstName().equals(firstName)) {
                found = true;
                break;
            }
        }
        return found;
    }

    private boolean allResultsContainExpectedLastName(List<Student> resultList, String lastName) {
        boolean found = false;
        for (Student p : resultList) {
            if (p.getLastName().equals(lastName)) {
                found = true;
                break;
            }
        }
        return found;
    }
}
