package com.baeldung.distinct;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "student")
@Data
@ToString(exclude = "school")
@EqualsAndHashCode(of = "id")
public class Student {

    @Id
    @Column(name = "student_id")
    private int id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "birth_year")
    private int birthYear;

    @ManyToOne
    @JoinColumn(name = "school_id", referencedColumnName = "school_id")
    private School school;

}
