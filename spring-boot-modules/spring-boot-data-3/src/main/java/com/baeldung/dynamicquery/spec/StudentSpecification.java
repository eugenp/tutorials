package com.baeldung.dynamicquery.spec;

import com.baeldung.dynamicquery.model.School;
import com.baeldung.dynamicquery.model.Student;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {

    public static Specification<Student> nameEndsWithIgnoreCase(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase());
    }

    public static Specification<Student> isAge(int age) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("age"), age);
    }

    public static Specification<Student> isSchoolBorough(String borough) {
        return (root, query, criteriaBuilder) -> {
            Join<Student, School> scchoolJoin = root.join("school");
            return criteriaBuilder.equal(scchoolJoin.get("borough"), borough);
        };
    }

}
