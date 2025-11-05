package com.baeldung.distinct;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "school")
@Data
@ToString(exclude = "students")
@EqualsAndHashCode(of = "id")
public class School {

    @Id
    @Column(name = "school_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", length = 100)
    private String name;

    @OneToMany(mappedBy = "school")
    private List<Student> students;

}
