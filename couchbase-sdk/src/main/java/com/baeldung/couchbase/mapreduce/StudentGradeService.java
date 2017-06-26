package com.baeldung.couchbase.mapreduce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewResult;
import com.couchbase.client.java.view.ViewRow;

public class StudentGradeService {

    final CouchbaseKeyGenerator<StudentGrade> keyGenerator;
    final CouchbaseCluster cluster;
    final Bucket bucket;
    final ObjectMapper om = new ObjectMapper();
    final StudentGradeQueryBuilder queryBuilder;
    
    public StudentGradeService(CouchbaseKeyGenerator<StudentGrade> keyGenerator) {
        this.keyGenerator = keyGenerator;
        this.queryBuilder = new StudentGradeQueryBuilder();
        cluster = CouchbaseCluster.create("127.0.0.1");
        bucket = cluster.openBucket("baeldung-tutorial");
    }
    
    public String insert(StudentGrade studentGrade) throws DuplicateKeyException {
        String id = keyGenerator.generateKey(studentGrade);
        if(bucket.exists(id)) {
            throw new DuplicateKeyException("document already exists with key " + id);
        }
        JsonObject content = JsonObject.empty()
                .put("type", "StudentGrade")
                .put("name", studentGrade.getName())
                .put("course", studentGrade.getCourse())
                .put("grade", studentGrade.getGrade())
                .put("hours", studentGrade.getHours());
        JsonDocument doc = JsonDocument.create(id, content);
        bucket.insert(doc);
        return id;
    }
    
    public List<JsonDocument> findAll() {
        ViewQuery query = queryBuilder.findAll();
        ViewResult result = bucket.query(query);
        return extractDocuments(result);
    }
    
    private List<JsonDocument> extractDocuments(ViewResult result) {
        List<JsonDocument> docs = new ArrayList<>();
        for(ViewRow row : result.allRows()) {
            JsonDocument doc = row.document();
            docs.add(doc);
        }
        return docs;
    }
    
    public List<JsonDocument> findByCourse(String course) {
        ViewQuery query = queryBuilder.findByCourse(course);
        ViewResult result = bucket.query(query);
        return extractDocuments(result);
    }
    
    public List<JsonDocument> findByCourses(String... courses) {
        ViewQuery query = queryBuilder.findByCourses(courses);
        ViewResult result = bucket.query(query);
        return extractDocuments(result);
    }
    
    public List<JsonDocument> findByGradeInRange(int lower, int upper, boolean inclusiveEnd) {
        ViewQuery query = queryBuilder.findByGradeInRange(lower, upper, inclusiveEnd);
        ViewResult result = bucket.query(query);
        return extractDocuments(result);
    }
    
    public List<JsonDocument> findByGradeLessThan(int upper) {
        ViewQuery query = queryBuilder.findByGradeLessThan(upper);
        ViewResult result = bucket.query(query);
        return extractDocuments(result);
    }
    
    public List<JsonDocument> findByGradeGreaterThan(int lower) {
        ViewQuery query = queryBuilder.findByGradeGreaterThan(lower);
        ViewResult result = bucket.query(query);
        return extractDocuments(result);
    }
        
    public List<JsonDocument> findByCourseAndGradeInRange(String course, int minGrade, int maxGrade, boolean inclusiveEnd) {
        ViewQuery query = queryBuilder.findByCourseAndGradeInRange(course, minGrade, maxGrade, inclusiveEnd);
        ViewResult result = bucket.query(query);
        return extractDocuments(result);
    }
    
    public List<JsonDocument> findTopGradesByCourse(String course, int limit) {
        ViewQuery query = queryBuilder.findTopGradesByCourse(course, limit);
        ViewResult result = bucket.query(query);
        return extractDocuments(result);
    }
    
    public Map<String, Long> countStudentsByCourse() {
        ViewQuery query = ViewQuery.from("studentGrades", "countStudentsByCourse")
                .reduce()
                .groupLevel(1);
        ViewResult result = bucket.query(query);
        
        Map<String, Long> numStudentsByCourse = new HashMap<>();
        for(ViewRow row : result.allRows()) {
            JsonArray keyArray = (JsonArray) row.key();
            String course = keyArray.getString(0);
            long count = Long.valueOf(row.value().toString());
            numStudentsByCourse.put(course, count);
        }
        
        return numStudentsByCourse;
    }
    
    public Map<String, Long> sumCreditHoursByStudent() {
        ViewQuery query = ViewQuery.from("studentGrades", "sumHoursByStudent")
                .reduce()
                .groupLevel(1);
        ViewResult result = bucket.query(query);
        
        Map<String, Long> creditHoursByStudent = new HashMap<>();
        for(ViewRow row : result.allRows()) {
            String course = (String) row.key();
            long sum = Long.valueOf(row.value().toString());
            creditHoursByStudent.put(course, sum);
        }
        
        return creditHoursByStudent;
    }
    
    public Map<String, Long> sumGradePointsByStudent() {
        ViewQuery query = ViewQuery.from("studentGrades", "sumGradePointsByStudent")
                .reduce()
                .groupLevel(1);
        ViewResult result = bucket.query(query);
        
        Map<String, Long> gradePointsByStudent = new HashMap<>();
        for(ViewRow row : result.allRows()) {
            String course = (String) row.key();
            long sum = Long.valueOf(row.value().toString());
            gradePointsByStudent.put(course, sum);
        }
        
        return gradePointsByStudent;
    }
    
    public Map<String, Float> calculateGpaByStudent() {
        Map<String, Long> creditHoursByStudent = sumCreditHoursByStudent();
        Map<String, Long> gradePointsByStudent = sumGradePointsByStudent();
        
        Map<String, Float> result = new HashMap<>();
        for(Entry<String, Long> creditHoursEntry : creditHoursByStudent.entrySet()) {
            String name = creditHoursEntry.getKey();
            long totalHours = creditHoursEntry.getValue();
            long totalGradePoints = gradePointsByStudent.get(name);
            result.put(name, ((float) totalGradePoints / totalHours));
        }
        return result;
    }
}
