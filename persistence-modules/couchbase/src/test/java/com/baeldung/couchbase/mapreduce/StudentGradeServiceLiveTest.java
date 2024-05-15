package com.baeldung.couchbase.mapreduce;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.view.ViewResult;
import com.couchbase.client.java.view.ViewRow;

public class StudentGradeServiceLiveTest {
    private static final Logger logger = LoggerFactory.getLogger(StudentGradeServiceLiveTest.class);
            
    static StudentGradeService studentGradeService;
    static Set<String> gradeIds = new HashSet<>();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        studentGradeService = new StudentGradeService(new StudentGradeKeyGenerator());
        insertStudentGrade(new StudentGrade("John Doe", "History", 80, 3));
        insertStudentGrade(new StudentGrade("Jane Doe", "History", 89, 3));
        insertStudentGrade(new StudentGrade("Bob Smith", "History", 90, 3));
        insertStudentGrade(new StudentGrade("Mary Jones", "History", 92, 3));
        insertStudentGrade(new StudentGrade("Jane Doe", "Math", 59, 3));
        insertStudentGrade(new StudentGrade("Bob Smith", "Math", 91, 3));
        insertStudentGrade(new StudentGrade("Mary Jones", "Math", 86, 3));
        insertStudentGrade(new StudentGrade("John Doe", "Science", 85, 4));
        insertStudentGrade(new StudentGrade("Bob Smith", "Science", 97, 4));
        insertStudentGrade(new StudentGrade("Mary Jones", "Science", 84, 4));
    }
    
    private static void insertStudentGrade(StudentGrade studentGrade) {
        try {
            String id = studentGradeService.insert(studentGrade);
            gradeIds.add(id);
        } catch (DuplicateKeyException e) {
        }
    }

    @Test
    public final void whenFindAll_thenSuccess() {
        List<JsonDocument> docs = studentGradeService.findAll();
        printDocuments(docs);
    }

    @Test
    public final void whenFindByCourse_thenSuccess() {
        List<JsonDocument> docs = studentGradeService.findByCourse("History");
        printDocuments(docs);
    }
    
    @Test
    public final void whenFindByCourses_thenSuccess() {
        List<JsonDocument> docs = studentGradeService.findByCourses("History", "Science");
        printDocuments(docs);
    }
    
    @Test
    public final void whenFindByGradeInRange_thenSuccess() {
        List<JsonDocument> docs = studentGradeService.findByGradeInRange(80, 89, true);
        printDocuments(docs);
    }
    
    @Test
    public final void whenFindByGradeLessThan_thenSuccess() {
        List<JsonDocument> docs = studentGradeService.findByGradeLessThan(60);
        printDocuments(docs);
    }
    
    @Test
    public final void whenFindByGradeGreaterThan_thenSuccess() {
        List<JsonDocument> docs = studentGradeService.findByGradeGreaterThan(90);
        printDocuments(docs);
    }
    
    @Test
    public final void whenFindByCourseAndGradeInRange_thenSuccess() {
        List<JsonDocument> docs = studentGradeService.findByCourseAndGradeInRange("Math", 80, 89, true);
        printDocuments(docs);
    }
    
    @Test
    public final void whenFindTopGradesByCourse_thenSuccess() {
        List<JsonDocument> docs = studentGradeService.findTopGradesByCourse("Science", 2);
        printDocuments(docs);
    }
    
    @Test
    public final void whenCountStudentsByCourse_thenSuccess() {
        Map<String, Long> map = studentGradeService.countStudentsByCourse();
        printMap(map);
    }
    
    @Test
    public final void whenSumCreditHoursByStudent_thenSuccess() {
        Map<String, Long> map = studentGradeService.sumCreditHoursByStudent();
        printMap(map);
    }
    
    @Test
    public final void whenSumGradePointsByStudent_thenSuccess() {
        Map<String, Long> map = studentGradeService.sumGradePointsByStudent();
        printMap(map);
    }
    
    @Test
    public final void whenCalculateGpaByStudent_thenSuccess() {
        Map<String, Float> map = studentGradeService.calculateGpaByStudent();
        printGpaMap(map);
    }
    
    private void printMap(Map<String, Long> map) {
        for(Map.Entry<String, Long> entry : map.entrySet()) {
            logger.info(entry.getKey() + "=" + entry.getValue());
        }
    }

    private void printGpaMap(Map<String, Float> map) {
        for(Map.Entry<String, Float> entry : map.entrySet()) {
            logger.info(entry.getKey() + "=" + entry.getValue());
        }
    }

    private void printDocuments(List<JsonDocument> docs) {
        for(JsonDocument doc : docs) {
            String key = doc.id();
            logger.info(key + " = " + doc.content().toString());
        }
    }
    
    private void printViewResultDocuments(ViewResult result) {
        for(ViewRow row : result.allRows()) {
            JsonDocument doc = row.document();
            String key = doc.id();
            logger.info(key + "=" + doc.content().toString());
        }
    }
    
    private void printViewResultRows(ViewResult result) {
        for(ViewRow row : result.allRows()) {
            logger.info(row.toString());
        }
    }
}
