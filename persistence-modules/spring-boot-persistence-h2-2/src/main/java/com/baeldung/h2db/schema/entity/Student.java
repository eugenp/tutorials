package com.baeldung.h2db.schema.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student", schema = "test")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "studentId")
public class Student {

    @Id
    @Column(name = "student_id", length = 10)
    private String studentId;

    @Column(name = "name", length = 100)
    private String name;

}
