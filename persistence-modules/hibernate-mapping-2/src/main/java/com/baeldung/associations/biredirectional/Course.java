package com.baeldung.associations.biredirectional;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Course {

    @Id
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "course_student",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students;
    
       public List<Student> getStudents() {
        return students;
    }
 
    //getters and setters
}
