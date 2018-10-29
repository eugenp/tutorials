package com.baeldung.couchbase.mapreduce;

import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.view.ViewQuery;

public class StudentGradeQueryBuilder {

    final ObjectMapper om = new ObjectMapper();
    
    public ViewQuery findAll() {
        return ViewQuery.from("studentGrades", "findByCourse");
    }
    
    public ViewQuery findByCourse(String course) {
        return ViewQuery.from("studentGrades", "findByCourse")
                .key(course);
    }
    
    public ViewQuery findByCourses(String... courses) {
        return ViewQuery.from("studentGrades", "findByCourse")
                .keys(JsonArray.from(courses));
    }
    
    public ViewQuery findByGradeInRange(int lower, int upper, boolean inclusiveEnd) {
        return ViewQuery.from("studentGrades", "findByGrade")
                .startKey(lower)
                .endKey(upper)
                .inclusiveEnd(inclusiveEnd);
    }
    
    public ViewQuery findByGradeLessThan(int upper) {
        return ViewQuery.from("studentGrades", "findByGrade")
                .endKey(upper)
                .inclusiveEnd(false);
    }
    
    public ViewQuery findByGradeGreaterThan(int lower) {
        return ViewQuery.from("studentGrades", "findByGrade")
                .startKey(lower);
    }
        
    public ViewQuery findByCourseAndGradeInRange(String course, int minGrade, int maxGrade, boolean inclusiveEnd) {
        return ViewQuery.from("studentGrades", "findByCourseAndGrade")
                .startKey(JsonArray.from(course, minGrade))
                .endKey(JsonArray.from(course, maxGrade))
                .inclusiveEnd(inclusiveEnd);
    }
    
    public ViewQuery findTopGradesByCourse(String course, int limit) {
        return ViewQuery.from("studentGrades", "findByCourseAndGrade")
                .startKey(JsonArray.from(course, 100))
                .endKey(JsonArray.from(course, 0))
                .inclusiveEnd(true)
                .descending()
                .limit(limit);
    }
    
    public ViewQuery countStudentsByCourse() {
        return ViewQuery.from("studentGrades", "countStudentsByCourse")
                .reduce()
                .groupLevel(1);
    }
    
    public ViewQuery sumCreditsByStudent() {
        return ViewQuery.from("studentGrades", "sumCreditsByStudent")
                .reduce()
                .groupLevel(1);
    }
}
